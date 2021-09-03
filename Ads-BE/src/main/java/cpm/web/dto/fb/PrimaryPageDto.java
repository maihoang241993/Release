package cpm.web.dto.fb;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PrimaryPageDto {
    private String id;
    private String name;
    private String category;
    private ArrayList<CategoryDto> category_list = new ArrayList<>();
}