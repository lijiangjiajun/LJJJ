


package task;

import app.FileMeta;
import dao.FileOperatorDAO;
import task.FileScanCallback;
import util.DBUtil;
import util.Pinyin4jUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FileOperateTask implements FileScanCallback {
    @Override
    public void execute(File dir) {
        if(dir.isDirectory()){
            //本地文件
            File[] children = dir.listFiles();
            List<FileMeta> localMetas = compose(children);
            //数据库文件，jdbc实现(根据路径查询所在文件位置)
            List<FileMeta> metas = FileOperatorDAO.query(dir.getPath());

            //本地有数据库没有的文件
            for(FileMeta localMeta:localMetas){
                if(!metas.contains(localMeta)){
                    FileOperatorDAO.insert(localMeta);//将这个数据插入数据库
                }
            }

            //数据库有本地文件没有
            for(FileMeta meta: metas){
                if(!localMetas.contains(meta)){
                    //如果是文件夹还得删除子文件
                    FileOperatorDAO.delete(meta);//删除数据库的这个数据
                }
            }
        }

    }

    private List<FileMeta> compose(File[] children) {
        List<FileMeta> metas = new ArrayList<>();
        if(children!=null){
            for(File child :children){
                metas.add(new FileMeta(child.getName(),child.getParent(),
                        child.length(),child.lastModified(),child.isDirectory()));
            }
        }
        return metas;
    }
}
