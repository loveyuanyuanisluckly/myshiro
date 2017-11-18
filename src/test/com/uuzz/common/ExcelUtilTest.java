package src.test.com.uuzz.common;

import com.uuzz.common.util.ExcelUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zj
 * @desc
 * @date 2017/11/8
 * @since 1.7
 */
public class ExcelUtilTest {

    public static void main(String[] args) throws FileNotFoundException {

        ExcelUtil<ExcelTestVO> excelUtil = new ExcelUtil(ExcelTestVO.class);

        OutputStream out = new FileOutputStream(new File("C:\\Users\\zj\\Desktop\\test.xlsx"));

        String sheetName = "test";

        List<ExcelTestVO> data = generateData();

        excelUtil.writeExcelFromList(data,sheetName,out);
    }

    private static List<ExcelTestVO> generateData(){
        List<ExcelTestVO> datas = new ArrayList<>(100);
        for (int i = 0; i <100 ; i++) {
            ExcelTestVO vo = new ExcelTestVO();
            vo.setAge(i+12);
            vo.setId(i);
            vo.setName("zj"+i);
            vo.setRemark("hy"+i);
            vo.setPhone("1008611");
            vo.setCreateDate(new Date());
            datas.add(vo);
        }
        return datas;
    }
}
