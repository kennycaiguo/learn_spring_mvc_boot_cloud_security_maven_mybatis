MyBatis 的 **`<trim>`** 标签是动态 SQL 的核心组件之一，主要用于**拼接前缀/后缀**以及**去除多余的字符或关键字**。`<where>` 和 `<set>` 标签底层实际上就是通过 `<trim>` 实现的。

------

一、 `<trim>` 的四大核心属性

通过 [CSDN博客](https://blog.csdn.net/good_good_xiu/article/details/122690774) 及相关文档可知，`<trim>` 包含以下 4 个常用属性：

- **`prefix`**：在 `<trim>` 包裹的 SQL 片段**前面**添加指定的前缀（例如：`WHERE`、`SET`、`(VALUES(`）。
- **`suffix`**：在 `<trim>` 包裹的 SQL 片段**后面**添加指定的后缀（例如：`)`）。
- **`prefixOverrides`**：指定需要**去除的开头**字符或关键字（多个条件可用 `|` 隔开，如 `AND |OR `），会在拼接 `prefix` 之前自动去除匹配项。
- **`suffixOverrides`**：指定需要**去除的结尾**字符或关键字（如 `,`），会自动去掉 SQL 片段末尾多余的符号。 

二、 常见应用场景与代码示例

1. 代替 `<where>` 标签：去除多余的 `AND` / `OR` 并拼接 `WHERE`

在多条件查询中，动态拼接条件往往会多出一个 `AND` 或 `OR`：

xml

```
<select id="findUserByCondition" resultType="User">
    SELECT * FROM user
    <trim prefix="WHERE" prefixOverrides="AND |OR ">
        <if test="name != null and name != ''">
            AND name = #{name}
        </if>
        <if test="age != null">
            AND age = #{age}
        </if>
    </trim>
</select>
```

 

- **效果**：如果两个条件都成立，SQL 为 `WHERE name = #{name} AND age = #{age}`；如果只有第二个条件成立，`prefixOverrides` 会自动把开头的 `AND ` 抹掉，最终拼接成 `WHERE age = #{age}`。
- 代替 `<set>` 标签：用于更新语句（去除末尾多余的逗号）

在 `UPDATE` 操作中，选择性更新字段时常会在末尾多出一个逗号 `,`： [[1](https://blog.csdn.net/good_good_xiu/article/details/122690774), [2](https://comate.baidu.com/zh/page/pk7b9gp8wvk)]

xml

```
<update id="updateUser">
    UPDATE user
    <trim prefix="SET" suffixOverrides=",">
        <if test="name != null">
            name = #{name},
        </if>
        <if test="age != null">
            age = #{age},
        </if>
    </trim>
    WHERE id = #{id}
</update>
```

 

- **效果**：`prefix="SET"` 会在最前方加上 `SET`，而 `suffixOverrides=",|"` 会自动清除最后一个字段后面多余的逗号。
- 用于批量插入或复杂括号包裹的 SQL

可以配合前后缀拼接括号：

xml

```
<insert id="insertUser">
    INSERT INTO user (name, age)
    <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
        #{name},
        #{age},
    </trim>
</insert>
```

 