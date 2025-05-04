package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path = "/karyawan")
public class ControllerKaryawan {
    @Autowired
    private RepositoryKaryawan repositoryKaryawan;

    public String generateNip() {
        Integer id = (int) repositoryKaryawan.count() + 1;
        return "NIP-" + String.format("%03d", id);
    }

    @GetMapping("/perusahaan")
    @ResponseBody
    public Object getPerusahaan() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("nama", "PT. APA AJA");
        obj.put("alamat", "Jln. APA AJA 2");
        return obj;
    }

    // menggunakan request parameter(param)
    @PostMapping(path = "/add")
    @ResponseBody
    public boolean addKaryawan(@RequestParam String nama, @RequestParam Double gaji) {
        ModelKaryawan k = new ModelKaryawan();
        String nip = generateNip();

        k.setId(nip);
        k.setNama(nama);
        k.setGaji(gaji);
        // JPA
        repositoryKaryawan.save(k);
        return true;
    }

    // menggunakan request body
    @PostMapping(path = "/add2")
    public @ResponseBody boolean addKaryawan(@RequestBody ModelKaryawan karyawan) {
        // JPA
        repositoryKaryawan.save(karyawan);
        return true;
    }

    @GetMapping("/getall")
    public @ResponseBody Iterable<ModelKaryawan> getAllKaryawan() {
        return repositoryKaryawan.findAll();
    }

}
