package com.shopping.controller;


import com.shopping.controller.vo.ItemVO;
import com.shopping.exception.BusinessException;
import com.shopping.result.CommonReturn;
import com.shopping.service.IItemService;
import com.shopping.service.dto.ItemDTO;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品模块.
 * Created by TongHaiJun
 * 2019/8/24 2:05
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController {

    @Autowired
    private IItemService iItemService;

    /**
     * 创建商品
     */
    @PostMapping("/createItem")
    @ResponseBody
    public CommonReturn createItem(@RequestParam(name = "title") String title,
                                   @RequestParam(name = "price") BigDecimal price,
                                   @RequestParam(name = "description") String description,
                                   @RequestParam(name = "stock") Integer stock,
                                   @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        // 封装service请求用来创建商品
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitle(title);
        itemDTO.setPrice(price);
        itemDTO.setDescription(description);
        itemDTO.setStock(stock);
        itemDTO.setImgUrl(imgUrl);
        ItemDTO itemDTOForReturn = iItemService.createItem(itemDTO);

        // 转换 itemDTO -> itemVO
        ItemVO itemVO = this.convertItemVOFromItemDTO(itemDTOForReturn);

        return CommonReturn.create(itemVO);
    }

    /**
     * 商品详情
     */
    @GetMapping("getItem")
    @ResponseBody
    public CommonReturn getItem(@RequestParam(name = "id") Integer id) {
        ItemDTO itemDTO = iItemService.getItemById(id);

        ItemVO itemVO = this.convertItemVOFromItemDTO(itemDTO);

        return CommonReturn.create(itemVO);
    }

    /**
     * 商品列表
     */
    @GetMapping("listItem")
    @ResponseBody
    public CommonReturn listItem() {
        List<ItemDTO> itemDTOList = iItemService.listItem();

        // 使用stream api将list内的itemDTO转化为itemVO
        List<ItemVO> itemVOList = itemDTOList.stream().map(itemDTO -> {
            ItemVO itemVO = this.convertItemVOFromItemDTO(itemDTO);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturn.create(itemVOList);
    }

    private ItemVO convertItemVOFromItemDTO(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }

        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemDTO, itemVO);

        if (itemDTO.getPromoDTO() != null) {
            // 有正在进行或即将进行的秒杀活动
            itemVO.setPromoId(itemDTO.getPromoDTO().getPromoStatus());
            itemVO.setPromoPrice(itemDTO.getPromoDTO().getPromoItemPrice());
            itemVO.setPromoStartDate(itemDTO.getPromoDTO().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoStatus(itemDTO.getPromoDTO().getPromoStatus());
        } else {
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }
}
