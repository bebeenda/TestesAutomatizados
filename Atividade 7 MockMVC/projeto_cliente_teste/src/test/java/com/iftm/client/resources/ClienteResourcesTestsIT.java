package com.iftm.client.resources;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteResourcesTestsIT {

    @Autowired
    private MockMvc mockMvc;

    //aqui pega um id que já existe no banco, no import.sql, conforme ordem de inserimento
    private Long idExistente;

    //id que não existe no banco
    private Long idInexistente;

    //aqui eu consigo puxar o total de clientes que foram inseridos no banco
    private Long totalClientes;

    @BeforeEach
    void setUp(){

        idExistente   = 3L;   // id da Clarice Lispector no banco
        idInexistente = 100L; // id que não existe em nenhum registro
        totalClientes = 12L;  // quantidade de INSERTs no import.sql
    }

    //Inicio dos casos de testes:

    // Caso de teste: Verifica se o endpoint GET/clients retorna uma página com todos os clientes cadastrados no banco de dados, por isso vou usar o findAll

    @Test
    @DisplayName("findAll retorna a página com todos os clientes")
    public void findAllDeveRetornarPagicaComTodosOsClientes() throws Exception{ //aqui o JUnit pega qualquer falha de teste com esse throws


        //Act
                                //É o método que dispara a requisição simulada ao chamar a API
        ResultActions resultado = mockMvc.perform(get("/clients")
                    .accept(MediaType.APPLICATION_JSON)); //recebo a resposta em formato de JSON

        //Assert
        // verificação do status 200
        resultado
            .andExpect(status().isOk())

         // verifica que "content" existe e é um array
        .andExpect(jsonPath("$.content").exists())
        .andExpect(jsonPath("$.content").isArray())

        // verifica total de elementos no banco
        .andExpect(jsonPath("$.totalElements").value(totalClientes))

        // verifica que está na primeira página
        .andExpect(jsonPath("$.number").value(0))

        // verifica que os campos essenciais existem no primeiro elemento
            .andExpect(jsonPath("$.content[0].id").exists())
            .andExpect(jsonPath("$.content[0].name").exists())
            .andExpect(jsonPath("$.content[0].cpf").exists())
            .andExpect(jsonPath("$.content[0].income").exists())
            .andExpect(jsonPath("$.content[0].birthDate").exists())
            .andExpect(jsonPath("$.content[0].children").exists())

             // verifica que clientes específicos do import.sql estão na lista
            .andExpect(jsonPath("$.content[?(@.name == 'Clarice Lispector')]").exists())
            .andExpect(jsonPath("$.content[?(@.name == 'Jose Saramago')]").exists());
            //esse terceiro aqui seria mais ou menos "Existe algum elemento na lista onde name == 'Clarice Lispector'?" pq pode ser que os registros podem variar dependendo do banco, daí nao tem como assumir que a Clarice seria sempre a posição 0


    }

    //Teste de findByID
    //Verificar se o endPoint GET/clientes/{id} retorna os dados referente ao cliente do id informado
    //aqui vou testar o da Clarice Lispector que é o 3

    @Test
    @DisplayName("findById deve retornar o cliente correto com o id existente")
    public void findByIdDeveRetornarClienteQuandoIdExiste() throws Exception{

        //Act
        ResultActions resultado = mockMvc.perform(get("/clients/id/{id}", idExistente, idExistente).accept(MediaType.APPLICATION_JSON));

        //Assert
        // verifica status 200
        resultado
            .andExpect(status().isOk())

        // verifica que a resposta é um objeto único e não um array
            .andExpect(jsonPath("$").isMap())

               // verifica cada campo com os valores reais do import.sql
            .andExpect(jsonPath("$.id").value(idExistente))
            .andExpect(jsonPath("$.name").value("Clarice Lispector"))
            .andExpect(jsonPath("$.cpf").value("10919444522"))
            .andExpect(jsonPath("$.income").value(3800.0))
            .andExpect(jsonPath("$.birthDate").value("1960-04-13T07:50:00Z"))
            .andExpect(jsonPath("$.children").value(2));      

    }

    //Teste de id Inexistente
   // Caso de teste: Verificar se o endpoint GET /clients/{id} retorna status 404 e o corpo de erro correto quando o id não existe no banco.

     @Test
    @DisplayName("findById deve retornar 404 e corpo de erro quando o id não existe")
    public void findByIdDeveRetornar404QuandoIdNaoExiste() throws Exception {
 
        // Act
        ResultActions resultado = mockMvc.perform(get("/clients/id/{id}", idInexistente)
                .accept(MediaType.APPLICATION_JSON));
 
        // Assert
        resultado
            // verifica status 404 Not Found
            .andExpect(status().isNotFound())
 
            // verifica que o campo timestamp existe no corpo de erro
            .andExpect(jsonPath("$.timestamp").exists())
 
            // verifica o código de status dentro do corpo
            .andExpect(jsonPath("$.status").value(404))
 
            // verifica a mensagem de erro padrão
            .andExpect(jsonPath("$.error").value("Resource not found"))
 
            // verifica a mensagem de detalhe
            .andExpect(jsonPath("$.message").value("Entity not found"))
 
            // verifica o path da requisição que gerou o erro
            .andExpect(jsonPath("$.path").value("/clients/id/" + idInexistente));
    }

}
