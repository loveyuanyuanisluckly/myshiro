package src.test.com.uuzz.common;

import com.uuzz.common.annotation.ExcelAttribute;

import java.util.Date;

/**
 * @author zj
 * @desc
 * @date 2017/11/8
 * @since 1.7
 */
public class ExcelTestVO {

    @ExcelAttribute(name="ID",isExport=true)
    private Integer id;
    @ExcelAttribute(name="姓名",isExport=true)
    private String name;
    @ExcelAttribute(name="电话",isExport=true)
    private String phone;
    @ExcelAttribute(name="年龄",isExport=true)
    private Integer age;
    @ExcelAttribute(name="备注")
    private String remark;
    @ExcelAttribute(name="创建时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
