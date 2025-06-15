package com.sistematurnos.service;

import java.util.Map;

public interface IEmailService {

    public void enviarEmailConHtml(String para, String asunto, String nombreTemplate, Map<String, Object> variables);
}

