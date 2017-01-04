package cn.edu.bjtu.weibo.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.WeiboDAO;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.PictureService;
import cn.edu.bjtu.weibo.service.UserSendedPictureService;

@Service("userSendedPictureService")
public class UserSendedPictureServiceImpl implements UserSendedPictureService {
	@Autowired
	private UserDAO userDao;
	@Autowired
	private WeiboDAO weiboDao;
	@Autowired
	private PictureService pictureService;
	
	@Override
	public List<Picture> getUserSendedPicture(String userId, int pageIndex, int numberPerPage) {
		List<Picture> l=new ArrayList<Picture>();
		int start=(pageIndex-1)*numberPerPage+1;
		int end=pageIndex*numberPerPage;
		int num=userDao.getWeiboNumber(userId);
		int count=0;
		int count1=0;
		for(int i=1;i<=num;i++){
			List<String> list=userDao.getWeibo(userId, i, 1);
			List<String> list2=weiboDao.getWeiboPicurl(list.get(0));
			count1=count+list2.size();
			if(count1>=start&&count<end){
				if(count<start){
					for(int j=start-count;j<=list2.size();j++){
						l.add(pictureService.getPicture(list2.get(j-1)));
					}
				}
				else if(count1>=end){
					for(int j=1;j<=list2.size()+end-count1;j++){
						l.add(pictureService.getPicture(list2.get(j-1)));
				    	 }
                                    break;
			    	}
				else{
					for(int j=1;j<=list2.size();j++){
						l.add(pictureService.getPicture(list2.get(j-1)));
				     }
				}
		    }
			count=count1;
		}
		
		return l;	
	}

}
