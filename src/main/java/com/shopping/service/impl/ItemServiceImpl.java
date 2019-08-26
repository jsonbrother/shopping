package com.shopping.service.impl;

import com.shopping.dao.ItemDao;
import com.shopping.dao.ItemStockDao;
import com.shopping.dao.dataObject.ItemDO;
import com.shopping.dao.dataObject.ItemStockDO;
import com.shopping.enums.BusinessErrorEnum;
import com.shopping.exception.BusinessException;
import com.shopping.service.IItemService;
import com.shopping.service.IPromoService;
import com.shopping.service.dto.ItemDTO;
import com.shopping.service.dto.PromoDTO;
import com.shopping.validator.ValidationResult;
import com.shopping.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by TongHaiJun
 * 2019/8/24 1:32
 */
@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemStockDao itemStockDao;

    @Autowired
    private IPromoService iPromoService;

    @Override
    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO) throws BusinessException {
        // 校验入参
        ValidationResult result = validator.validate(itemDTO);
        if (result.isHasErrors()) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        // 转化itemDTO -> dataObject
        ItemDO itemDO = this.convertItemDOFromItemDTO(itemDTO);

        // 写入数据库（需获取商品id）
        itemDao.insert(itemDO);
        itemDTO.setId(itemDO.getId());

        ItemStockDO itemStockDO = this.convertItemStockDOFromItemDTO(itemDTO);
        itemStockDao.insert(itemStockDO);

        // 返回创建完成的对象
        return this.getItemById(itemDTO.getId());
    }

    @Override
    public ItemDTO getItemById(Integer id) {
        // 1.查询商品信息
        ItemDO itemDO = itemDao.selectById(id);

        if (itemDO == null) {
            return null;
        }

        // 2.获得商品库存数量
        ItemStockDO itemStockDO = itemStockDao.selectByItemId(itemDO.getId());
        if (itemStockDO == null) {
            return null;
        }

        // 3.转化itemDO itemStockDO -> itemDTO 返回出去
        ItemDTO itemDTO = this.convertItemDTOFromItemDOAndItemStockDO(itemDO, itemStockDO);

        // 4.判断是否进行秒杀活动
        PromoDTO promoDTO = iPromoService.getPromoByItemId(itemDTO.getId());
        if (promoDTO != null && promoDTO.getPromoStatus() != 3) {
            itemDTO.setPromoDTO(promoDTO);
        }

        return itemDTO;
    }

    @Override
    public List<ItemDTO> listItem() {
        List<ItemDO> itemDOList = itemDao.selectAll();

        List<ItemDTO> itemDTOList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDao.selectByItemId(itemDO.getId());
            return this.convertItemDTOFromItemDOAndItemStockDO(itemDO, itemStockDO);
        }).collect(Collectors.toList());

        return itemDTOList;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow = itemStockDao.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDao.increaseSales(itemId, amount);
    }

    private ItemDO convertItemDOFromItemDTO(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }

        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemDTO, itemDO);
        itemDO.setPrice(itemDTO.getPrice().doubleValue());

        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemDTO(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }

        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemDTO.getId());
        itemStockDO.setStock(itemDTO.getStock());

        return itemStockDO;
    }

    private ItemDTO convertItemDTOFromItemDOAndItemStockDO(ItemDO itemDO, ItemStockDO itemStockDO) {
        if (itemDO == null || itemStockDO == null) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(itemDO, itemDTO);
        itemDTO.setPrice(new BigDecimal(itemDO.getPrice()));
        itemDTO.setStock(itemStockDO.getStock());

        return itemDTO;
    }
}
