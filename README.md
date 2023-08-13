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

## Exemplos práticos
### Aplicação etag-header
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
