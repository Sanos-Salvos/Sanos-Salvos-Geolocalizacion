package com.sanossalvos.geolocalizacion.factory;

import com.sanossalvos.geolocalizacion.dto.UbicacionDTO;
import com.sanossalvos.geolocalizacion.model.Ubicacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UbicacionFactoryImplTest {

    @InjectMocks
    private UbicacionFactoryImpl factory;

    @Test
    void crearEntidad_deberiaMapearTodosLosCampos() {
        UbicacionDTO dto = UbicacionDTO.builder()
                .mascotaId(10L)
                .direccion("Av. Principal")
                .ciudad("Santiago")
                .latitud(-33.44)
                .longitud(-70.66)
                .build();

        Ubicacion entidad = factory.crearEntidad(dto);

        assertEquals(10L, entidad.getMascotaId());
        assertEquals("Av. Principal", entidad.getDireccion());
        assertEquals("Santiago", entidad.getCiudad());
        assertEquals(-33.44, entidad.getLatitud());
        assertEquals(-70.66, entidad.getLongitud());
    }

    @Test
    void crearDTO_deberiaMapearTodosLosCampos() {
        Ubicacion entidad = new Ubicacion();
        entidad.setMascotaId(5L);
        entidad.setDireccion("Calle Norte");
        entidad.setCiudad("Valparaiso");
        entidad.setLatitud(-33.05);
        entidad.setLongitud(-71.62);

        UbicacionDTO dto = factory.crearDTO(entidad);

        assertEquals(5L, dto.getMascotaId());
        assertEquals("Calle Norte", dto.getDireccion());
        assertEquals("Valparaiso", dto.getCiudad());
        assertEquals(-33.05, dto.getLatitud());
        assertEquals(-71.62, dto.getLongitud());
    }

    @Test
    void crearEntidad_conCamposNulos_deberiaRetornarEntidadConNulos() {
        UbicacionDTO dto = new UbicacionDTO();

        Ubicacion entidad = factory.crearEntidad(dto);

        assertNotNull(entidad);
        assertNull(entidad.getMascotaId());
        assertNull(entidad.getDireccion());
    }

    @Test
    void crearDTO_conCamposNulos_deberiaRetornarDTOConNulos() {
        Ubicacion entidad = new Ubicacion();

        UbicacionDTO dto = factory.crearDTO(entidad);

        assertNotNull(dto);
        assertNull(dto.getMascotaId());
    }

    @Test
    void roundTrip_deberiaPreservarDatos() {
        UbicacionDTO original = UbicacionDTO.builder()
                .mascotaId(1L)
                .direccion("Test Dir")
                .ciudad("Test City")
                .latitud(-30.0)
                .longitud(-70.0)
                .build();

        Ubicacion entidad = factory.crearEntidad(original);
        UbicacionDTO resultado = factory.crearDTO(entidad);

        assertEquals(original.getMascotaId(), resultado.getMascotaId());
        assertEquals(original.getDireccion(), resultado.getDireccion());
        assertEquals(original.getCiudad(), resultado.getCiudad());
        assertEquals(original.getLatitud(), resultado.getLatitud());
        assertEquals(original.getLongitud(), resultado.getLongitud());
    }
}
