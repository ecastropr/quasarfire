# QuasarFire
El servicio que se encuentra alojado en este repositorio git, nos permite obtener le posición de la nave de Quasar junto con un mensaje de auxilio que esta enviando.

Este proyecto esta realizado con SpringBoot & Java

# Pasos para Ejecución

Después de haber clonado el repositorio abre una consola y situate en la ruta del proyecto y ejecuta el siguiente comando para realizar la instalación de dependecias maven.

***mvn clean install***

Una vez realizada la instalación de dependencias puedes ejecutar el proyecto de forma local por medio del siguiente comando (por defecto este se desplegara en el puerto 8080),
si se desea desplegar en un puerto distino se puede agregar (***server.port="numPuerto"***) en el archivo ***application.poperties***

***mvn spring-boot:run***

Para el consumo de los servicios expuestos puedes consumir las siguientes Apis desde postman los cuales se encuentran expuestos en AWS:
  - http://quasarfire-env.eba-bpepkz2m.us-east-2.elasticbeanstalk.com/api/quasarfire/topsecret
  - http://quasarfire-env.eba-bpepkz2m.us-east-2.elasticbeanstalk.com/api/quasarfire/topsecret_split/:satelite_name --cambiar satelite_name por el nombre del satelite de cual desea enviar información

Se referencian los documentos del swagger y la colección de postman:

  - [Swagger](swagger/Quasar%20Fire%20Operation_swagger.json)
  - [Postman-Collection](swagger/Quasar%20Fire%20Operation%20AWS_postman.json) -- En este archivo podras encontrar ejemplos del consumo de los servicios

# Recuerda
Siempre ejecutar los test unitarios para validar el funcionamiento correcto de las funcionalidades ya implementadas, de igual manera adiciona pruebas unitarias para las funcionalidades nuevas.
