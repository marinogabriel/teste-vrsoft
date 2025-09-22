Esse repositório contém o código para o backend em Spring e algumas configurações docker que utilizei para a comunicação entre as partes do sistema localmente, já que após testar as credenciais no servidor fornecido, constatei que o usuário não tinha autorização para interagir no nível necessário.

## Obs: como rodei a aplicação e o RabbitMQ no mesmo container docker para teste local, será necessário alterar o host no "application.properties"

Essa foi minha primeira vez programando um sistema com mensageria, embora já tenha estudado o conceito em um alto nível anteriormente.
Portanto, toda essa parte de configuração/debug de devops acabaram tomando bastante tempo, talvez a maioria, e tenho certeza que cometi alguns atentados contra as boas práticas nesse campo.

Porém no final consegui realizar a comunicação entre todas as partes, espero que de forma satisfatória :)

Considerações finais: 

-Criei um enum para os status, mas acabei não tendo tempo para utilizá-lo, porém seria bastante pertinente nesse caso.

-Também pensei em JsonView para reduzir os payloads de retorno para o mínimo necessário, mas a entidade criada já foi bastante minimalista (e não sobrou tempo)

-Pude aprender mais sobre mensageria na prática, infelizmente estourei o tempo e não pude entregar tudo o que gostaria, principalmente na questão do tratamento de erros/exceções e também aprimoramentos na interface.

Obrigado pelo desafio e pela oportunidade!
