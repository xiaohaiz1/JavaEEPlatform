package cn.edu.xmu.restfuldemo.controller;

import cn.edu.xmu.restfuldemo.controller.vo.GoodsVo;
import cn.edu.xmu.restfuldemo.model.Goods;
import cn.edu.xmu.restfuldemo.util.ResponseCode;
import cn.edu.xmu.restfuldemo.util.ReturnObject;
import io.swagger.annotations.*;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.edu.xmu.restfuldemo.service.GoodsService;
import cn.edu.xmu.restfuldemo.util.ResponseUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * 商品控制器
 * @author Ming Qiu
 */
@Api(value = "商品API", tags = "商品API")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/goods", produces = "application/json;charset=UTF-8")
public class GoodsController {

    private final Log logger = LogFactory.getLog(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation(value = "获得一个商品对象",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="商品对象id" ,required = true)
    })
    @ApiResponses({
    })
    @GetMapping("{id}")
    public Object getGoodsById(@PathVariable("id") Integer id) {
        ReturnObject<Goods> returnObject =  goodsService.findById(id);

        if (returnObject.getErrno().equals(ResponseCode.RESOURCE_ID_NOTEXIST)){
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return returnObject;
    }

    @ApiOperation(value = "用商品名称搜索",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "name", value ="商品名称", required = true)
    })
    @ApiResponses({
    })
    @GetMapping("search")
    public Object searchGoodsByName(@RequestParam String name) {
        return goodsService.searchByName(name);
    }

    @ApiOperation(value = "新建商品",  produces="application/json")
    @ApiImplicitParams({
    })
    @ApiResponses({
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createGood(@Validated @RequestBody GoodsVo goodsVo){
        return goodsService.createGoods(goodsVo);
    }

    @ApiOperation(value = "修改商品",  produces="application/json")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @PutMapping("{id}")
    public Object modiGood(@PathVariable Integer id, @RequestBody GoodsVo goodsVo){

        ReturnObject<Object> returnObject = goodsService.modifyGoods(id, goodsVo, 1);
        if (returnObject.getErrno().equals(ResponseCode.RESOURCE_ID_NOTEXIST)){
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return returnObject;
    }

    @ApiOperation(value = "删除商品",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="商品对象id" ,required = true)
    })
    @ApiResponses({
    })
    @DeleteMapping("{id}")
    public Object delGoods(@PathVariable("id") Integer id) {
        ReturnObject<Object> returnObject = goodsService.delGoods(id);
        if (returnObject.getErrno().equals(ResponseCode.RESOURCE_ID_NOTEXIST)){
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return returnObject;
    }

}
