package kr.co.fastcampus.eatgo.interfaces;

import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;
import kr.co.fastcampus.eatgo.application.RegionService;
import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;


    @GetMapping("/regions")
    public List<Region> list(){
        List<Region> regions = regionService.getRegions();

        return regions;
    }

    @PostMapping("/regions")
    public ResponseEntity<?> create(
            @Valid @RequestBody Region resource
    ) throws URISyntaxException {

        Region region = regionService.addRegion(resource.getName());

        URI location = new URI("/region/" + region.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
