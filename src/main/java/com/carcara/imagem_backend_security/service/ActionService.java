package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.model.sharing.Action;
import com.carcara.imagem_backend_security.model.sharing.CreateActionDTO;
import com.carcara.imagem_backend_security.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;
    public Object createAction(CreateActionDTO actionDTO) {
        return actionRepository.save(new Action(actionDTO));
    }

    public List<Action> listActions() {
        return actionRepository.findAll();
    }
}
