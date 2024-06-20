package com.carcara.imagem_backend_security.controller;

import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.ValueDTO;
import com.carcara.imagem_backend_security.model.sharing.Action;
import com.carcara.imagem_backend_security.model.sharing.CreateActionDTO;
import com.carcara.imagem_backend_security.model.sharing.CreateWebhookDTO;
import com.carcara.imagem_backend_security.model.sharing.SharingConfig;
import com.carcara.imagem_backend_security.repository.ActionRepository;
import com.carcara.imagem_backend_security.service.ActionService;
import com.carcara.imagem_backend_security.service.SharingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/sharing")
public class SharingController {

    @Autowired
    private SharingService sharingService;

    @Autowired
    private ActionService actionService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("create-webhook")
    public ResponseEntity createWebhook(@RequestBody CreateWebhookDTO createWebhookDTO) throws Exception {
        sharingService.createWebhook(createWebhookDTO);
        return ResponseEntity.ok(createWebhookDTO);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("create-action")
    public ResponseEntity createAction (CreateActionDTO action){
        return ResponseEntity.ok(actionService.createAction(action));
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<Action> listActions(){
        return actionService.listActions();
    }

    @PostMapping("decript-sharing/{shareToken}")
    public ResponseEntity decriptSharing(@RequestBody ValueDTO share, @PathVariable("shareToken") String shareToken) throws Exception {
        return ResponseEntity.ok(sharingService.decriptSharing(share,shareToken));
    }


}
