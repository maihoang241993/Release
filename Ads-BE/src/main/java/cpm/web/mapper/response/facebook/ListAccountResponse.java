package cpm.web.mapper.response.facebook;

import cpm.web.dto.fb.DatumDto;
import cpm.web.dto.fb.PagingDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ListAccountResponse {
    public ArrayList<DatumDto> data = new ArrayList<>();
    public PagingDto paging;
}