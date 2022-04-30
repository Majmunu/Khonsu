package com.halo.khonsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen
 * @since 2022-04-29
 */
@Getter
@Setter
  @TableName("t_answers")
@ApiModel(value = "Answers对象", description = "")
public class Answers implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("评论内容")
      private String content;

      @ApiModelProperty("评论人id")
      private Integer userId;

      @ApiModelProperty("评论时间")
      private String time;

      @ApiModelProperty("父id")
      private Integer pid;
  @TableField(exist = false)
  private String pNickname;  // 父节点的用户昵称
  @TableField(exist = false)
  private Integer pUserId;  // 父节点的用户id

      @ApiModelProperty("最上级评论id")
      private Integer originId;

      @ApiModelProperty("关联问答的id")
      private Integer questionId;

  @TableField(exist = false)
  private String nickname;
  @TableField(exist = false)
  private String avatarUrl;

  @TableField(exist = false)
  private List<Answers> children;


}
