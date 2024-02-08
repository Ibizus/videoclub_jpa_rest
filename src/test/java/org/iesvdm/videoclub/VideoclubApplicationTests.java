package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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
        Tutorial tutorial2 = new Tutorial(0, "Titulo del tutorial", "descripcion tuto", false, new Date(), new HashSet<>());

        // CON BUILDER DE LOMBOK PUEDO OMITIR CAMPOS Y ES MÁS DINAMICO:
        Tutorial tutorial = Tutorial.builder()
                .titulo("Titulo del tutorial")
                .publicado(true)
                .descripcion("descripcion tuto")
                // Tengo que inicializarlo porque si no es NULL
                // y no puedo usarlo (List) o guardarlo en bbdd (fecha NOT NULL)
                .fechaPublicacion(new Date())
                .comentarios(new HashSet<>())
                //
                .build();

        Comentario comentario1 = Comentario.builder()
                .texto("Comentario1")
                //.tutorial(tutorial)
                .build();
        //tutorial.getComentarios().add(comentario1);

        // SOLO PUEDE HABER UN BUILDER SEGUIDO PORQUE SETEA ID=0 POR DEFECTO
        // POR LO QUE GRABAS EN DOS VECES O CONSTRUYES DE OTRA FORMA:
        Comentario comentario2 = Comentario.builder()
                .texto("Comentario2")
                //.tutorial(tutorial)
                .build();
        //tutorial.getComentarios().add(comentario2);
        Comentario comentario3 = new Comentario(-1, "Comentario3", tutorial);


        // DE ESTA MANERA ESO EL HELPER PARA EVITAR SETEAR TOTURIAL CADA VEZ
        // AHORRO LINEAS Y AÑADO TODOS LOS COMENTARIOS DE UNA VEZ.
        tutorial.addComentario(comentario1)
                .addComentario(comentario3);

        tutorialRepository.save(tutorial);
    }

    @Test
    @Transactional // Para poder acceder a comentarios
    void pruebaEliminarComentario(){

        var optionalTutorial = this.tutorialRepository.findById(1L);
        optionalTutorial.ifPresent(tutorial -> {
                tutorial
                        .getComentarios()
                        .forEach(System.out::println);

                var optionalComentario = tutorial.getComentarios().stream().findFirst();

                tutorial.removeComentario(optionalComentario.get());

                // Puedo usar save porque tengo Cascade ALL
            // ¿PORQUE NO ESTÁ BORRANDO? (se borra en tutorial pero no persiste en bbdd)
                // (ERA POR EL FLUSH)
                this.tutorialRepository.save(tutorial);
        });
        // ESTE SI FUNCIONA:
        this.tutorialRepository.delete(optionalTutorial.get());

        // Tengo que cerrar la sesion porque estoy en @transactional:
        this.tutorialRepository.flush();
    }


}
