package cronapi;

import cronapi.CronapiMetaData;
import cronapi.ParamMetaData;
import cronapi.*;
import java.util.concurrent.Callable;

/**
 * Função para testar os campos da criação do plugin ...
 *
 * @author Francisco Iago De Sousa Mendes
 * @version 1.0
 * @since 2021-07-27
 *
 */

@CronapiMetaData(categoryName = "Pagamento")
public class PagamentoPixJuno {

@CronapiMetaData(type = "function", name = "Pegar chave de acesso Juno", description = "Função que faz uma requisição à API da Juno para pegar a chave de acesso bearer.")
public static Var getAccessToken(@ParamMetaData(description = "clientID") Var clientID, @ParamMetaData(description = "clientSecret") Var clientSecret) throws Exception {
 return new Callable<Var>() {

   private Var idempotencyKey = Var.VAR_NULL;
   private Var res = Var.VAR_NULL;
   private Var err = Var.VAR_NULL;

   public Var call() throws Exception {

    idempotencyKey =
    cronapi.util.Operations.generateUUID();

    try {

        res =
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("sucesso",
        Var.VAR_TRUE) , Var.valueOf("dados",
        cronapi.util.Operations.getURLFromOthers(
        Var.valueOf("POST"),
        Var.valueOf("application/x-www-form-urlencoded"),
        Var.valueOf("https://sandbox.boletobancario.com/authorization-server/oauth/token"), Var.VAR_NULL,
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("Authorization",
        Var.valueOf(
        Var.valueOf("Basic ").toString() +
        cronapi.conversion.Operations.StringToBase64(
        Var.valueOf(
        clientID.toString() +
        Var.valueOf(":").toString() +
        clientSecret.toString())).toString()))),
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("grant_type",
        Var.valueOf("client_credentials"))),
        Var.valueOf(""),
        Var.valueOf("BODY"))) , Var.valueOf("idempotency",idempotencyKey));
     } catch (Exception err_exception) {
          err = Var.valueOf(err_exception);

        res =
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("sucesso",
        Var.VAR_FALSE) , Var.valueOf("dados",err) , Var.valueOf("idempotency",idempotencyKey));
     }
    return res;
   }
 }.call();
}

@CronapiMetaData(type = "function", name = "Gerar nova chave pix Juno", description = "Função que faz uma requisição à API da Juno para gerar uma nova chave pix.")
public static Var getPixKey(@ParamMetaData(description = "clientID") Var clientID, @ParamMetaData(description = "clientSecret") Var clientSecret, @ParamMetaData(description = "privateToken") Var privateToken) throws Exception {
 return new Callable<Var>() {

   private Var idempotencyKey = Var.VAR_NULL;
   private Var res = Var.VAR_NULL;
   private Var err = Var.VAR_NULL;
   private Var authRequest = Var.VAR_NULL;
   private Var authData = Var.VAR_NULL;
   private Var acessToken = Var.VAR_NULL;

   public Var call() throws Exception {

    authRequest =
    getAccessToken(clientID, clientSecret);

    authData =
    cronapi.json.Operations.toJson(
    cronapi.json.Operations.getJsonOrMapField(authRequest,
    Var.valueOf("dados")));

    acessToken =
    cronapi.json.Operations.getJsonOrMapField(authData,
    Var.valueOf("access_token"));

    idempotencyKey =
    cronapi.json.Operations.getJsonOrMapField(authRequest,
    Var.valueOf("idempotency"));

    try {

        res =
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("sucesso",
        Var.VAR_TRUE) , Var.valueOf("dados",
        cronapi.util.Operations.getURLFromOthers(
        Var.valueOf("POST"),
        Var.valueOf("application/json"),
        Var.valueOf("https://sandbox.boletobancario.com/api-integration/pix/keys"), Var.VAR_NULL,
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("Authorization",
        Var.valueOf(
        Var.valueOf("Bearer ").toString() +
        acessToken.toString())) , Var.valueOf("X-Idempotency-Key",idempotencyKey) , Var.valueOf("X-Resource-Token",privateToken) , Var.valueOf("X-Api-Version",
        Var.valueOf("2"))),
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("type",
        Var.valueOf("RANDOM_KEY"))),
        Var.valueOf(""),
        Var.valueOf("BODY"))));
     } catch (Exception err_exception) {
          err = Var.valueOf(err_exception);

        res =
        cronapi.map.Operations.createObjectMapWith(Var.valueOf("sucesso",
        Var.VAR_FALSE) , Var.valueOf("dados",err));
     }
    return res;
   }
 }.call();
}

}


