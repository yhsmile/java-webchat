/**   
* @Title: ArticleResponseMessage.java
* @Package: com.smile.webchat.message.response
* @Description: 
* @author: 杨辉  
* @date: 2017年6月20日 上午10:04:09
*
*/
package com.smile.webchat.message.response;

import java.util.List;

/**
* @ClassName: ArticleResponseMessage
* @Description: 图文消息
* @author: 杨辉
* @date: 2017年6月20日 上午10:04:09
* 
*/
public class ArticleResponseMessage {

	/**
	 *  图文消息个数，限制为10条以内
	 */
    private int ArticleCount;
    
    /**
     *  多条图文消息信息，默认第一个item为大图
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
