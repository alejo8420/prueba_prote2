package proteccion.escalador.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import proteccion.escalador.demo.servicesimpl.GestorImagenServiceImpl;

@SpringBootTest
class ProyectoEscaladorApplicationTests {

    @Test
    void imageIsBiggerThanA4() {
        GestorImagenServiceImpl gst = new GestorImagenServiceImpl();

        assertEquals(true, gst.verificarTamano(15, 797));
    }
    
    @Test
    void imageIsSmallerThanA4() {
        GestorImagenServiceImpl gst = new GestorImagenServiceImpl();

        assertEquals(false, gst.verificarTamano(15, 795));
    }
}
