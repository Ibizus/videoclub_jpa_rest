package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
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
}
