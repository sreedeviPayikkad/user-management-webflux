package io.springwebflux.usermanagement.controller;

import io.springwebflux.usermanagement.entities.Department;
import io.springwebflux.usermanagement.mapstruct.dto.DepartmentDTO;
import io.springwebflux.usermanagement.mapstruct.mapper.UserManagementObjectMapper;
import io.springwebflux.usermanagement.repository.DepartmentRepository;
import io.springwebflux.usermanagement.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Slf4j
public class DepartmentRepositoryIntegrationTest {

    @Autowired
    ConnectionFactoryInitializer initializer;

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private DepartmentController departmentController;
    @Mock
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserManagementObjectMapper userManagementObjectMapper;

    private DepartmentDTO departmentDTORequest;
    private DepartmentDTO departmentDTOResponse;
    private Department department;

    @BeforeEach
    public void setup() {
        departmentDTOResponse = DepartmentDTO.builder().id(1L).name("Marketing").location("Chicago").build();
        departmentDTORequest = DepartmentDTO.builder().name("Marketing").location("Chicago").build();
        department = Department.builder().id(1L).name("Marketing").location("Chicago").build();
        when(userManagementObjectMapper.departmentToDepartmentDTO(any(Department.class))).thenReturn(departmentDTORequest);
        when(userManagementObjectMapper.departmentDTOToDepartment(any(DepartmentDTO.class))).thenReturn(department);
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Test
    public void testCreateDepartment() {
        webTestClient.post()
                .uri("/v1/department")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(departmentDTORequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(DepartmentDTO.class)
                .value(r -> {
                    assertEquals(r.getId(), departmentDTOResponse.getId());
                    assertEquals(r.getName(), departmentDTOResponse.getName());
                    assertEquals(r.getLocation(), departmentDTOResponse.getLocation());
                });
    }

}