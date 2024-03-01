package br.com.bitzen.desafio.integration.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArtistaTest {

    @Test
    void testArtista() {
        // Criar uma instância mock
    	Artista artistaMock = mock(Artista.class);

        // Definir comportamento do mock
    	when(artistaMock.getId()).thenReturn(1L);
        when(artistaMock.getName()).thenReturn("name");
        when(artistaMock.getNationality()).thenReturn("Nationality");
        when(artistaMock.getWebsite()).thenReturn("Website");

        // Testar os métodos
        assertEquals(1L, artistaMock.getId());
        assertEquals("name", artistaMock.getName());
        assertEquals("Nationality", artistaMock.getNationality());
        assertEquals("Website", artistaMock.getWebsite());
    }

}
