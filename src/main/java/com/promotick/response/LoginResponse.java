package com.promotick.response;

public class LoginResponse {

    private Long id;
    private String email;
    private String token;

    private String nombre;

    public LoginResponse(Long id, String email, String token, String nombre) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
