package cpm.web.mapper.response.facebook;

import cpm.web.dto.telegram.DataDto;
import cpm.web.dto.telegram.PagingDto;

import java.util.ArrayList;

public class FbResponse {
    ArrayList< DataDto > data = new ArrayList <> ();
    PagingDto PagingObject;


    // Getter Methods

    public PagingDto getPaging() {
        return PagingObject;
    }

    public ArrayList< DataDto > getData() {
        return data;
    }

    // Setter Methods

    public void setPaging(PagingDto pagingObject) {
        this.PagingObject = pagingObject;
    }

    public void setData(ArrayList<DataDto> listData) {
        ArrayList<DataDto> data_list = new ArrayList<>();
        for (DataDto data: listData) {
            data_list.add(data);
        }
        this.data = data_list;
    }





}
