package com.gcrespo.demoparkapi;

import com.gcrespo.demoparkapi.dto.UsuarioCreateDto;
import com.gcrespo.demoparkapi.dto.UsuarioResponseDto;
import com.gcrespo.demoparkapi.dto.UsuarioSenhaDto;
import com.gcrespo.demoparkapi.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    void createUsuarioComUserNameEPasswordValidosDeveRetornarUsuarioCriadoComStatus201() {

        UsuarioResponseDto response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email.com", "123456"))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getUsername()).isEqualTo("tody@email.com");
        Assertions.assertThat(response.getRole()).isEqualTo("CLIENTE");

    }

    @Test
    void createUsuarioComUserNameInvalidosDeveRetornarErrorMessageComStatus422() {

        ErrorMessage response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@", "123456"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);


        response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email", "123456"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

    }

    @Test
    void createUsuarioComPasswordInvalidosDeveRetornarErrorMessageComStatus422() {

        ErrorMessage response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email.com", ""))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email.com", "1234"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);


        response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@email.com", "1234567"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

    }

    @Test
    void createUsuarioComUserNameJaCriadoDeveRetornarErrorMessageStatus409() {

        ErrorMessage response = testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("ana@email.com", "123456"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);

    }

    @Test
    void buscarUsuarioComIdExistenteDeveRetornarUsuarioComStatus200() {

        UsuarioResponseDto response = testClient.get()
                .uri("/api/v1/usuarios/100")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(100);
        Assertions.assertThat(response.getUsername()).isEqualTo("ana@email.com");
        Assertions.assertThat(response.getRole()).isEqualTo("ADMIN");

    }

    @Test
    void buscarUsuarioComIdInexistenteDeveRetornarErrorMessageComStatus404() {

        ErrorMessage response = testClient.get()
                .uri("/api/v1/usuarios/0")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }

    @Test
    void editarSenhaComDadosValidadosDeveRetornarStatus204() {

        testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123457", "123457"))
                .exchange()
                .expectStatus()
                .isNoContent();

    }

    @Test
    void editarSenhaComIdInexistenteDeveRetornarStatus404() {

        ErrorMessage response = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();


        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }

    @Test
    void editarSenhaComCamposInvalidosDeveRetornarStatus422() {

        ErrorMessage response = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "", ""))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        response = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        response = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("1234567", "1234567", "1234567"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

    }

    @Test
    void editarSenhaComSenhasInvalidasDeveRetornarStatus400() {

        ErrorMessage response = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "000000"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(400);

        response = testClient.patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("000000", "123456", "123456"))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(400);


    }

}
