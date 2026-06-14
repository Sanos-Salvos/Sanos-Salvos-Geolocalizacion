package com.sanossalvos.geolocalizacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "mapaExterno", url = "https://nominatim.openstreetmap.org")
public interface MapaExternoClient {
    @GetMapping(value = "/search?format=json", headers = "User-Agent=SanosYSalvosApp/1.0 (contacto@sanossalvos.com)")
    List<Map<String, Object>> buscarCoordenadas(@RequestParam("q") String query);
}