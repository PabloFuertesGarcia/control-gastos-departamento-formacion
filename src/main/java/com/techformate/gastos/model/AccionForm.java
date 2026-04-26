package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccionForm {

    private Integer id;
    private String habilidad;
    private String denominacion;

}
