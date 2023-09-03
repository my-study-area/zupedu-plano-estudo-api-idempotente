# zupedu-plano-estudo-api-idempotente
Plano de estudo - Desenhando REST APIs Idempotentes e Tolerantes a falhas

## Links
- [Video - Idempotência: Conheça o que é e como o protocolo HTTP a usa (10 min | pt_BR)](https://www.youtube.com/watch?v=-50uDb_hExw&ab_channel=MaurodeBoni)
- [Video - O que são chaves de idempotência? (8 min | pt_BR)](https://www.youtube.com/watch?v=U0DyJx68oCY&ab_channel=CristianoCunha)
- [Texto - O que é ser idempotente em REST? (12 min | pt_BR)](https://www.infoq.com/br/news/2013/05/idempotent/)
- [Texto - O que é idempotência e porque devemos utilizar (3 min | pt_BR)](https://xuenqui.medium.com/idempot%C3%AAncia-uma-boa-pr%C3%A1tica-a-se-utilizar-em-servi%C3%A7os-rest-633c38f4d7c0)
- [Texto - Idempotência por Mozilla Docs (5 min | pt_BR)](https://developer.mozilla.org/pt-BR/docs/Glossary/Idempotent)
- [Texto - Guia de REST APIs da Zalando: Sessão: Atendendo as propriedades comumns dos metodos: Idempotency, Cacheable e Safe.(20 min | en)](https://opensource.zalando.com/restful-api-guidelines/#149)
- [Texto - Como a Stripe lida com requisições idempotentes (4 min | en)](https://stripe.com/docs/api/idempotent_requests)
- [Video - Idempotência e Retentivas com Stripe (5min | en)](https://stripe.com/docs/videos/developer-foundations?video=idempotency-and-retries&lang=java)
- [Texto - Zup Edu: 3 estratégias para escrita de REST APIs Idempotentes com Spring Boot (15 min | pt_BR)](https://github.com/zup-academy/materiais-publicos-treinamentos/blob/main/crud-basico-com-java-hibernate/idempotencia-em-rest-apis.md)
- [Texto - Conceitos de Idempotência com OpenPix (pt_BR)](https://developers.openpix.com.br/docs/concepts/idempotence)
- [Texto - Fundamentos de APIs Seveless - Idempotência - Tradução (15 min | pt_BR)](https://dev.to/oieduardorabelo/fundamentos-de-api-serverless-idempotencia-2d5m)
- [Texto - Serverless API Essentials - Idempotency (15 min | en)](https://www.readysetcloud.io/blog/allen.helton/api-essentials-idempotency/)
- [Texto - Como implementar APIs Idempotentes (14 min | en)](https://asyncq.com/how-to-implement-idempotent-api-part-1)
- [Texto - Fazendo retries seguros com APIs Idempotentes (20 min | en)](https://aws.amazon.com/pt/builders-library/making-retries-safe-with-idempotent-APIs/?did=ba_card&trk=ba_card)
- [Texto - Repetindo chamadas que falharam com Segurança (20 min | en)](https://www.tedinski.com/2019/02/20/idempotence.html)
- [Texto - Draft para uma RFC para Idempotency Key (30 min| en)](https://datatracker.ietf.org/doc/draft-ietf-httpapi-idempotency-key-header/)
- [Texto - Implemetando Idempotencia em ambientes AWS Serveless (7 min | en)](https://qasimalbaqali.medium.com/achieving-idempotency-in-the-aws-serverless-space-d0671a521479)
- [Video - Você sabe o que é idempotência? Uma abordagem prática com Java e Kafka (1h 16min | pt_br)](https://www.youtube.com/watch?v=uSXAln1cfqU&ab_channel=DXLab)
- [IDEMPOTÊNCIA: O que é e como implementar com Redis](https://www.youtube.com/watch?v=h1zRfNJtTYA)
- [Arquitetura - Resolvendo Race Condition com Distributed Lock](https://www.youtube.com/watch?v=9yB7DYq1PNs)
- [Escrevendo Clients e Services Tolerantes a Falhas com Rafael Ponte | 💻 Zup Open Talks 🚀](https://www.youtube.com/watch?v=TMmN9cR_IsM&ab_channel=Zup)
## Exemplos práticos
### Aplicação utilizando primary key com etag e If-None-Match
- [spring boot etag header example](https://javadeveloperzone.com/spring-boot/spring-boot-etag-header-example/)
https://github.com/gregwhitaker/etag-example/blob/master/src/main/java/example/etag/service/config/ETagConfiguration.java

```bash
cd exemplos-praticos/etag-header
curl --location 'localhost:8080/hello' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > GET /hello HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > 
# < HTTP/1.1 200 
# < ETag: "07d793b78c60fb2b2c265e8c3114bc321"
# < Content-Type: text/plain;charset=UTF-8
# < Content-Length: 17
# < Date: Thu, 10 Aug 2023 00:47:36 GMT
# < 
# * Connection #0 to host localhost left intact
# Hello etag Header

curl --header 'If-None-Match: "07d793b78c60fb2b2c265e8c3114bc321"' --location 'http://localhost:8080/hello' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > GET /hello HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > If-None-Match: "07d793b78c60fb2b2c265e8c3114bc321"
# > 
# < HTTP/1.1 304 
# < ETag: "07d793b78c60fb2b2c265e8c3114bc321"
# < Date: Thu, 10 Aug 2023 00:47:46 GMT
# < 
# * Connection #0 to host localhost left intact
```
> Obs: o teste também pode ser realizado utilizando um browser com Developer tools habilitado, na aba Network, para visualizar as informações do header da requisição.

### Aplicação utilizando primary key, Secondary-Key e Idempotency-Key

```bash
# entra no diretório
cd exemplos-praticos/nossa-biblioteca/

# inicia o redis
docker-compose up -d
```

Requisição com primary-key utilizando If-Match:
```bash
curl --location 'localhost:8080/api/payments1' \
--header 'If-Match: b79b29bf-5e3a-4e69-a39d-7cd523409cf9' \
--header 'Content-Type: application/json' \
--data '{
    "paymentAmount": "100.00",
    "transactionId": "b79b29bf-5e3a-4e69-a39d-7cd523409cf9",
    "currency": "BRL"
}' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > POST /api/payments1 HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > If-Match: b79b29bf-5e3a-4e69-a39d-7cd523409cf9
# > Content-Type: application/json
# > Content-Length: 117
# > 
# * upload completely sent off: 117 out of 117 bytes
# < HTTP/1.1 201 
# < Location: http://localhost:8080/api/payments1/1
# < Content-Length: 0
# < Date: Tue, 22 Aug 2023 01:03:11 GMT
# < 
# * Connection #0 to host localhost left intact

curl --location 'localhost:8080/api/payments1' \
--header 'If-Match: b79b29bf-5e3a-4e69-a39d-7cd523409cf9' \
--header 'Content-Type: application/json' \
--data '{
    "paymentAmount": "100.00",
    "transactionId": "b79b29bf-5e3a-4e69-a39d-7cd523409cf9",
    "currency": "BRL"
}' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > POST /api/payments1 HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > If-Match: b79b29bf-5e3a-4e69-a39d-7cd523409cf9
# > Content-Type: application/json
# > Content-Length: 117
# > 
# * upload completely sent off: 117 out of 117 bytes
# < HTTP/1.1 412 
# < Content-Type: application/json
# < Transfer-Encoding: chunked
# < Date: Tue, 22 Aug 2023 01:04:20 GMT
# < 
# * Connection #0 to host localhost left intact
# {"timestamp":"2023-08-22T01:04:20.828+00:00","status":412,"error":"Precondition Failed","path":"/api/payments1"}
```

Realizando uma requisição utilizando um método com Secondary-Key
```bash
curl --location 'localhost:8080/api/payments2' \
--header 'Content-Type: application/json' \
--data '{
    "paymentAmount": "100.00",
    "transactionId": "63673167-0b3c-41c6-9642-6cae67ce5303",
    "currency": "BRL"
}' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > POST /api/payments2 HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > Content-Type: application/json
# > Content-Length: 117
# > 
# * upload completely sent off: 117 out of 117 bytes
# < HTTP/1.1 201 
# < Location: http://localhost:8080/api/payments2/4
# < Content-Length: 0
# < Date: Tue, 22 Aug 2023 01:09:32 GMT
# < 
# * Connection #0 to host localhost left intact

curl --location 'localhost:8080/api/payments2' \
--header 'Content-Type: application/json' \
--data '{
    "paymentAmount": "100.00",
    "transactionId": "63673167-0b3c-41c6-9642-6cae67ce5303",
    "currency": "BRL"
}' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > POST /api/payments2 HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > Content-Type: application/json
# > Content-Length: 117
# > 
# * upload completely sent off: 117 out of 117 bytes
# < HTTP/1.1 409 
# < Content-Type: application/json
# < Transfer-Encoding: chunked
# < Date: Tue, 22 Aug 2023 01:06:45 GMT
# < 
# * Connection #0 to host localhost left intact
# {"timestamp":"2023-08-22T01:06:45.889+00:00","status":409,"error":"Conflict","path":"/api/payments2"}
```

Realizando uma requisição utilizando Idempotency-Key
```bash
curl --location 'localhost:8080/api/payments3' \
--header 'Idempotency-Key: 6d683b56-a6bf-4fcd-89d7-9d5e1c7848d6' \
--header 'Content-Type: application/json' \
--data '{
    "paymentAmount": "100.00",
    "transactionId": "6d683b56-a6bf-4fcd-89d7-9d5e1c7848d6",
    "currency": "BRL"
}' -v
# *   Trying 127.0.0.1...
# * TCP_NODELAY set
# * Connected to localhost (127.0.0.1) port 8080 (#0)
# > POST /api/payments3 HTTP/1.1
# > Host: localhost:8080
# > User-Agent: curl/7.58.0
# > Accept: */*
# > Idempotency-Key: 6d683b56-a6bf-4fcd-89d7-9d5e1c7848d6
# > Content-Type: application/json
# > Content-Length: 117
# > 
# * upload completely sent off: 117 out of 117 bytes
# < HTTP/1.1 201 
# < Location: http://localhost:8080/api/payments3/5
# < Content-Length: 0
# < Date: Tue, 22 Aug 2023 01:14:05 GMT
# < 
# * Connection #0 to host localhost left intact
```
