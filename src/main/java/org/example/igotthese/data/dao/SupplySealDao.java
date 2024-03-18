package org.example.igotthese.data.dao;

import java.util.List;
import org.example.igotthese.data.entity.SupplySeal;

public interface SupplySealDao {
    SupplySeal saveSupplySeal(SupplySeal supplySeal);
    List<SupplySeal> getAllSupplySeal();
    List<SupplySeal> getSupplySealByPostId(Long postId);
    void deleteSupplySealById(Long id);
}
