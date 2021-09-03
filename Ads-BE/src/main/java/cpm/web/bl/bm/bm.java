package cpm.web.bl.bm;

import cpm.web.mapper.request.bm.BmRequest;

import java.util.List;

public interface bm {
    List<?> loadBm(BmRequest data);

    boolean updateBm(BmRequest data);

    boolean deleteBm(BmRequest data);

    boolean updateTokenBm(BmRequest data);
}
