package com.ibm.gerenciador_tarefas.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErroResponse {

        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public ErroResponse(int status, String error, String message, String path) {
            this.timestamp = LocalDateTime.now();
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }

}

