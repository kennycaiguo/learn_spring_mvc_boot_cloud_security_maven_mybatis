Spring 配置文件中的 **p 命名空间**和 **c 命名空间**是用于简化 XML 依赖注入（DI）的快捷语法，直接在 `<bean>` 标签上以属性形式配置，减少冗长的子标签。

核心定位与区别

- **p 命名空间（Property）**：简化 **Setter 方法注入**，替代 `<property>` 子标签。
- **c 命名空间（Constructor）**：简化 **构造方法注入**，替代 `<constructor-arg>` 子标签。

| 维度             | p 命名空间                                           | c 命名空间                                          |
| ---------------- | ---------------------------------------------------- | --------------------------------------------------- |
| **对应传统标签** | `<property>`                                         | `<constructor-arg>`                                 |
| **注入方式**     | Setter 方法                                          | 构造方法                                            |
| **命名空间声明** | `xmlns:p="http://www.springframework.org/schema/p"`  | `xmlns:c="http://www.springframework.org/schema/c"` |
| **实体类要求**   | 需要对应属性的 `setXxx()` 方法（通常配合无参构造器） | 需要对应参数的构造方法                              |

1. p 命名空间详解

声明方式

在 `<beans>` 根标签中添加 `xmlns:p` 声明（无需单独指定 XSD 地址）： [[1](https://developer.aliyun.com/article/1209025)]

语法规则与示例

- 

- **注入普通值**：`p:属性名="值"`
- **注入 Bean 引用**：`p:属性名-ref="目标Bean的id"`
- 

例如，将传统 `<property>` 写法简化为属性形式，完整细节可参考 [Spring——依赖注入之p命名空间和c命名空间](https://blog.csdn.net/qq_43980232/article/details/145041484)。

2. c 命名空间详解

声明方式与语法规则

在 `<beans>` 根标签中添加 `xmlns:c` 声明后，支持两种注入方式： [[1](https://developer.aliyun.com/article/459784)]

- 

- **按参数名注入**：`c:参数名="值"` 或 `c:参数名-ref="目标Bean的id"`
- **按参数索引注入**（从 0 开始）：`c:_0="值"` 或 `c:_1-ref="目标Bean的id"`（适用于未保留参数名的情况）
- 

- 使用注意事项

1. **复杂集合限制**：`p` 和 `c` 命名空间适用于简单字面量和单值引用；List、Map 等复杂集合仍需使用传统 `<list>`、`<map>` 等标签。
2. **参数名可见性**：使用 `c:参数名` 注入时需确保编译时保留了参数名（如 `-parameters`），否则建议使用索引 `c:_0`。
3. **适用场景**：在现代 Spring Boot 或注解驱动项目中较少用于业务 Bean，主要用于维护遗留 XML 配置。