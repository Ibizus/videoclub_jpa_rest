package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VideoclubApplicationTests {

    @Autowired
    TutorialRepository tutorialRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    /* Esto me deja la sesion abierta con la bbdd para poder acceder
       a todo el contenido a traves de los proxys cargados,
       ya que al estar el fetch en LAZY no se carga todo
       el contenido en la primera consulta sql
    */
    void pruebaOneToMany(){
        var tutorialList = tutorialRepository.findAll();

        tutorialList.forEach(tutorial -> System.out.println(tutorial));
    }

    // Prueba grabar ONE TO MANY:
    @Test
    void pruebaGuardarOneTomany(){

        // ESTO DA FALLO PORQUE ME VA A PEDIR TODOS LOS ARGUMENTOS, POR LO QUE TENGO QUE METER ID=0 Y EL RESTO CON CADENA VACIA SI NO QUIERO METER DATOS.
        Tutorial tutorial2 = new Tutorial(0, "Titulo del tutorial", "descripcion tuto", false, null);

        // CON BUILDER DE LOMBOK PUEDO OMITIR CAMPOS Y ES MÁS DINAMICO:
        Tutorial tutorial = Tutorial.builder()
                .titulo("Titulo del tutorial")
                .publicado(true)
                .descripcion("descripcion tuto")
                .build();

        Comentario comentario1 = Comentario.builder()
                .texto("Comentario1")
                //.tutorial(tutorial)
                .build();
        //tutorial.getComentarios().add(comentario1);

        Comentario comentario2 = Comentario.builder()
                .texto("Comentario2")
                //.tutorial(tutorial)
                .build();
        //tutorial.getComentarios().add(comentario2);

        // DE ESTA MANERA ESO EL HELPER PARA EVITAR SETEAR TOTURIAL CADA VEZ
        // AHORRO LINEAS Y AÑADO TODOS LOS COMENTARIOS DE UNA VEZ.
        tutorial.addComentario(comentario1).addComentario(comentario2);

    }


}
