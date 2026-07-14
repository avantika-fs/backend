package com.example.demo;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.InquiryRequestDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.ProjectInquiry;
import com.example.demo.entity.SystemUser;
import com.example.demo.entity.enums.Role;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContractorAssignmentRepository;
import com.example.demo.repository.ProjectInquiryRepository;
import com.example.demo.repository.SystemUserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.InquiryService;
import com.example.demo.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BuildBridgeApplicationTests {

    private SystemUserRepository userRepository;
    private ProjectInquiryRepository inquiryRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private AuthService authService;
    private InquiryService inquiryService;

    @BeforeEach
    void setup() {
        userRepository = mock(SystemUserRepository.class);
        inquiryRepository = mock(ProjectInquiryRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        // Use real JwtService with test values to avoid ByteBuddy Java 25 issues
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret",
                "dGhpcyBpcyBhIHZlcnkgc2VjdXJlIHNlY3JldCBrZXkgZm9yIGJ1aWxkYnJpZGdl");
        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L);
        authenticationManager = mock(AuthenticationManager.class);
        authService = new AuthService(userRepository, passwordEncoder, jwtService, authenticationManager);
        inquiryService = new InquiryService(inquiryRepository, userRepository,
                mock(ContractorAssignmentRepository.class));
    }

    @Test
    void testRegisterClient() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("testclient");
        dto.setEmail("testclient@test.com");
        dto.setPassword("password");
        dto.setRole(Role.CLIENT);
        dto.setFullName("Test Client");

        SystemUser savedUser = SystemUser.builder()
                .id(1L).username("testclient").email("testclient@test.com")
                .password("encoded").role(Role.CLIENT).fullName("Test Client").build();

        when(userRepository.save(any(SystemUser.class))).thenReturn(savedUser);

        AuthResponseDto response = authService.register(dto);

        assertNotNull(response);
        assertEquals("testclient", response.getUsername());
        assertEquals(Role.CLIENT, response.getRole());
        assertNotNull(response.getToken());
    }

    @Test
    void testRegisterSupervisorThrows() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("supervisor1");
        dto.setEmail("sup@test.com");
        dto.setPassword("password");
        dto.setRole(Role.SUPERVISOR);
        dto.setFullName("Supervisor");

        assertThrows(RuntimeException.class, () -> authService.register(dto));
    }

    @Test
    void testAuthenticateSuccess() {
        AuthRequestDto dto = new AuthRequestDto();
        dto.setUsername("testclient");
        dto.setPassword("password");

        SystemUser user = SystemUser.builder()
                .id(1L).username("testclient").email("testclient@test.com")
                .password("encoded").role(Role.CLIENT).fullName("Test Client").build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsername("testclient")).thenReturn(Optional.of(user));

        AuthResponseDto response = authService.authenticate(dto);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals(Role.CLIENT, response.getRole());
    }

    @Test
    void testSubmitInquiry() {
        InquiryRequestDto dto = new InquiryRequestDto();
        dto.setSiteName("Green Valley");
        dto.setProjectType("HOUSE");
        dto.setLocation("Sector 45");
        dto.setBudget(new BigDecimal("150000"));

        SystemUser client = SystemUser.builder()
                .id(1L).username("client1").email("client1@test.com")
                .password("encoded").role(Role.CLIENT).fullName("Client One").build();

        ProjectInquiry saved = ProjectInquiry.builder()
                .id(1L).siteName("Green Valley").projectType("HOUSE")
                .location("Sector 45").client(client)
                .status(ProjectInquiry.InquiryStatus.PENDING).build();

        when(userRepository.findByUsername("client1")).thenReturn(Optional.of(client));
        when(inquiryRepository.save(any(ProjectInquiry.class))).thenReturn(saved);

        ProjectInquiry result = inquiryService.submitInquiry(dto, "client1");

        assertNotNull(result);
        assertEquals("Green Valley", result.getSiteName());
        assertEquals(ProjectInquiry.InquiryStatus.PENDING, result.getStatus());
    }

    @Test
    void testSubmitInquiryClientNotFound() {
        InquiryRequestDto dto = new InquiryRequestDto();
        dto.setSiteName("Test Site");
        dto.setProjectType("HOUSE");
        dto.setLocation("Test Location");

        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inquiryService.submitInquiry(dto, "unknown"));
    }

    @Test
    void testConvertToProjectInquiryNotFound() {
        when(inquiryRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inquiryService.convertToProject(999L, 1L));
    }

    @Test
    void testConvertToProjectAlreadyAssigned() {
        ProjectInquiry inquiry = ProjectInquiry.builder()
                .id(1L).siteName("Test").projectType("HOUSE").location("Loc")
                .status(ProjectInquiry.InquiryStatus.PLANNING).build();

        when(inquiryRepository.findById(1L)).thenReturn(Optional.of(inquiry));

        assertThrows(RuntimeException.class, () -> inquiryService.convertToProject(1L, 1L));
    }

    @Test
    void testGetMyInquiries() {
        SystemUser client = SystemUser.builder()
                .id(1L).username("client1").email("c@test.com")
                .password("enc").role(Role.CLIENT).fullName("Client").build();

        List<ProjectInquiry> inquiries = List.of(
                ProjectInquiry.builder().id(1L).siteName("Site A").projectType("HOUSE")
                        .location("Loc A").client(client).status(ProjectInquiry.InquiryStatus.PENDING).build()
        );

        when(userRepository.findByUsername("client1")).thenReturn(Optional.of(client));
        when(inquiryRepository.findByClientId(1L)).thenReturn(inquiries);

        List<ProjectInquiry> result = inquiryService.getMyInquiries("client1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Site A", result.get(0).getSiteName());
    }

    @Test
    void testResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        assertEquals("Resource not found", ex.getMessage());
    }

    @Test
    void testGetUsersByRole() {
        SystemUser contractor = SystemUser.builder()
                .id(2L).username("contractor1").email("c@test.com")
                .password("enc").role(Role.CONTRACTOR).fullName("Contractor").build();

        when(userRepository.findByRole(Role.CONTRACTOR)).thenReturn(List.of(contractor));

        List<SystemUser> result = authService.getUsersByRole(Role.CONTRACTOR);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Role.CONTRACTOR, result.get(0).getRole());
    }

    @Test
    void testJwtTokenGenerationAndValidation() {
        SystemUser user = SystemUser.builder()
                .id(1L).username("testuser").email("t@test.com")
                .password("enc").role(Role.CLIENT).fullName("Test").build();

        String token = jwtService.generateToken(user);
        assertNotNull(token);
        assertEquals("testuser", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, user));
    }
}
