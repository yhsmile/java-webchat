/**   
* @Title: ArticleResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: ���  
* @date: 2017��6��20�� ����10:04:09
*
*/
package com.smile.webchat.message.response;

import java.util.List;

/**
* @ClassName: ArticleResponseMessage
* @Description: ͼ����Ϣ
* @author: ���
* @date: 2017��6��20�� ����10:04:09
* 
*/
public class ArticleResponseMessage {

	/**
	 *  ͼ����Ϣ����������Ϊ10������
	 */
    private int ArticleCount;
    
    /**
     *  ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
     */
    private List<Article> Articles;
    
    
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
    
    
    
    
}
