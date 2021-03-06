package com.ty.blog.service;

import com.ty.blog.base.BaseService;
import com.ty.blog.pojo.Article;
import com.ty.blog.pojo.ResponseData;
import com.ty.blog.util.ResponseUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: UserService
 * @Description: 用户的业务类
 * @author zhangtainyi
 * @date 2019/9/17 11:16
 *
 */
@Service
public class ArticleService extends BaseService {

    /**
     * 获取作者所有的文章列表
     * @param author 作者名
     * @return
     */
    public List<Article> getArticlesByAuthor(String author) {
        List<Article> articles = articleDao.findAllByAuthor(author);
        return articles;
    }


    public boolean saveArticle(Map<String, Object> map){
        return true;
    }

    /**
     * 上传图片
     * @param req
     * @param image
     * @return
     */
    public ResponseData uploadArticleImg(HttpServletRequest req, MultipartFile image){
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return ResponseUtil.success(url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtil.failure(500, "上传失败");
    }
}
