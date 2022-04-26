//pacote
package petstore;
// biblioteca

import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;

//classe
public class Pet {
    //atributos = ex: rg, cpf, cor da pele, estado civil, endereço
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade pet

    //metodos (inclui,exclui, altera, consulta e funções = o que ira fazer
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    // incluir - create- Post

    @Test // identifica o metodo ou função como um teste para TestNG
    public void incluirPet() throws IOException{
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin
        given()//Dado
                .contentType("application/json") // application/json é comum em api rest
                .log().all()//log all mostra o que esta acontecendo
                .body(jsonBody)
       .when()
                .post(uri)//requisição com POST
       .then()
                .log().all()//log all mostra o que esta acontecendo traz o log
                .statusCode(200)
                .body("name",is ("Steve"))
                .body("status",is ("available"))
              .body("category.name",is ("ASXD32"))
        ;
    }
    @Test (priority=2)
    public void consultarPet(){
        String petId = "199222";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri +"/" + petId)// Requisição com GET
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Steve"))
                .body("category.name", is ("ASXD32"))
                .body("status", is ("available") )
                .extract()
                .path("category.name")
        ;
        System.out.println("o token é" + token)
        ;
    }
    @Test (priority=3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)

        .when()
                .put(uri) //requisição com PUT
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Steve"))
                .body("status", is("vendido"))
                ;
    }
    @Test
    public void excluirPet(){
    String petId = "199222";

    given()
            .contentType("application/json")
            .log().all()

        .when()
            .delete ( uri + "/" + petId) // Requisição com DELETE

        .then()
            .log().all()
            .statusCode(200)
            .body("code", is (200))
            .body("type" , is ("unknown"))
            .body("message", is (petId))
    ;
    }
}
