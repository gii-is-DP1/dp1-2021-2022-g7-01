# Samurai Sword

Este repositorio almacena el proyecto de desarrollo del juego de mesa [Samurai Sword](https://www.dvgiochi.com/catalogo/samurai-sword/?lang=eng). 
Además se encuentra desplegado en [Heroku](http://dp-samurai-sword.herokuapp.com/), aunque la funcionalidad de cambiar foto de perfil no funciona por problemas de compatibilidad.

# IMPORTANTE 

 - El proyecto se lanza en el puerto 9999.
 - Se debe activar la opción "Refresh using native hooks or polling" en Window -> Preferences -> General -> Workspace. Esto se debe hacer para que detecte las nuevas fotos subidas a la aplicación.

# Reglas básicas del juego

Las reglas que se describen a lo largo de este documento son:

- [Inicio de partida](#inicio-de-partida)

- [Turnos](#turnos)

- [Ataque](#ataque)

- [Perder todos los puntos de vida](#perder-todos-los-puntos-de-vida)

- [Fin de la baraja](#fin-de-la-baraja)

- [Fin de la partida](#fin-de-la-partida)

- [Link a reglas oficiales](#link-a-reglas-oficiales)

## Inicio de partida

Las partidas estarán formadas entre 4-7 jugadores. A cada jugador se le asignará aleatoriamente al comienzo un rol oculto para el resto de los jugadores, salvo el Shogun que es conocido por todo el mundo. Estos roles son Shogun, Samurái, Ninja y Ronin. En función del número de jugadores el número de roles presentes en la partida variará. Además, cada jugador tendrá también un personaje público para el resto. Cada carta de personaje indica la cantidad de vida y una habilidad única.

| N° jugadores | Shogun | Samuráis | Ninjas | Ronin |
|:---:|:--------:|:------:|:------:|:-----:|
| 4 jugadores | 1 | 1 | 2 | 0 |
| 5 jugadores | 1 | 1 | 2 | 1 |
| 6 jugadores | 1 | 1 | 3 | 1 |
| 7 jugadores | 1 | 2 | 3 | 1 |

Tras esto, se reparten los puntos de vida y de honor a los jugadores. Los puntos de vida vienen indicado en la carta del personaje como se ha comentado anteriormente. Por otra parte, el Shogun comenzará con 5 puntos de honor y el resto 3 si hay entre 4-5 jugadores o 4 si hay entre 6-7.

Por último, se repartirán las cartas de la siguiente forma, comenzando por el Shogun:

| Orden | Número de cartas |
|:-----:|:----------------:|
| Shogun | 4 |
| 2° y 3° jugador | 5 |
| 4° y 5° jugador | 6 |
| 6° y 7° jugador | 7 |

Tras esta preparación, se puede comenzar a jugar la partida.

## Turnos

El primer jugador en jugar su turno será el Shogun. Al principio del turno se deben robar 2 cartas de la baraja. Se podrá jugar tantas cartas como se quiera, aunque sólo se pueden utilizar un arma de ataque por turno. Existen 3 tipos de cartas en función de su color:

 - Amarillas: Cartas que realizan su efecto y se deshechan.

 - Azules: Cartas que se mantienen sobre la mesa y aplican su efecto constantemente.

 - Rojas: Armas de un solo uso que se utilizan para atacar.

Al final del turno, el número máximo de cartas en la mano son 7.

## Ataque

Las cartas de armas tienen dos números, el daño y el alcance. El daño es la cantidad de vidas que se le quitará al defensor si el golpe acierto y el alcance es la dificultad máxima a la que permite atacar. La dificultad se calcula contando el número de jugadores desde el atacante hasta el defensor, incluyéndolo. El ataque puede ser bloqueado con una carta de Parada y no haría daño al defensor.

## Perder todos los puntos de vida

Cuando un jugador pierde todos sus puntos de vida, deberá darle uno de sus puntos de honor al jugador que le haya debilitado. Este jugador recuperará todos sus puntos de vida cuando sea su turno, jugándolo con normalidad. Hasta entonces se encontrará en el estado de Indefenso y se le aplicará las siguientes normas:

 - No puede ser objetivo de ataques.
 - No es afectado por cartas que hagan daño en area como Jujutsu y Grito de Batalla.
 - No se cuenta para medir la dificultad de los ataques.

También se entrará en este estado si un jugador se queda sin cartas en la mano.

## Fin de la baraja

Cuando se acaba la baraja al robar cartas, la pila de descarte pasa a ser la baraja y todos los jugadores pierden un punto de honor.

## Fin de la partida

Cuando un juegador pierde todos sus puntos de honor o sólo queda un jugador con vida, se acaba automáticamente la partida. Para comprobar cuál es el equipo vencedor, todos los jugadores revelan sus roles y suman sus puntos de honor entre todos los miembros del mismo equipo. Algunos roles multiplicarán sus puntos de honor por un número en función del número de jugadores en la partida. Estos multiplicadores se reflejan en la siguiente tabla:

| N° jugadores | Shogun | Samuráis | Ninjas | Ronin |
|:---:|:--------:|:------:|:------:|:-----:|
| 4 jugadores | x1 | x2 | x1 / x2 | - |
| 5 jugadores | x1 | x1 | x1 | x2 |
| 6 jugadores | x1 | x2 | x1 | x3 |
| 7 jugadores | x1 | x1 | x1 | x3 |

## Link a reglas oficiales

Si tiene alguna duda o quiere profundizar en alguna regla, puede acceder a [las reglas oficiales de Samurai Sword](https://www.dvgiochi.com/giochi/samuraisword/download/EDGSS01_Rules_ES_PRINT.pdf)
