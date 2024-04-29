package com.example.clientes.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemCreateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int units;
    private int productId;

}
