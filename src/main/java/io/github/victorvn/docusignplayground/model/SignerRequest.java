package io.github.victorvn.docusignplayground.model;

import lombok.Data;

@Data
public class SignerRequest{
    private String name;
    private String email;
    private String roleName;
}