package com.ztravel.common.freemarker.xss;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.apache.commons.io.IOUtils;
import freemarker.cache.TemplateLoader;

/**
 * 对所有的ftl文件做html escape
 * <p><b>NOTE</b>:对于需要html标签的富文本，请用html白名单过滤
 * <p><b>NOTE</b>所有的页面信息，需要控制在后端输出,禁止在js里面拼接html后输出到页面
 * <p>
 * @author liuzhuo
 *
 */
public class HtmlEscapeTemplateLoader implements TemplateLoader{

	private static final String HTML_ESCAPE_PREFIX = "<#escape x as x?html>";
	private static final String HTML_ESCAPE_SUFFIX = "</#escape>";

	private final TemplateLoader delegate;

	public HtmlEscapeTemplateLoader(TemplateLoader delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		return delegate.findTemplateSource(name);
	}

	@Override
	public long getLastModified(Object templateSource) {
		return delegate.getLastModified(templateSource);
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		Reader reader = delegate.getReader(templateSource, encoding);
		String templateText = IOUtils.toString(reader);
		return new StringReader(HTML_ESCAPE_PREFIX + templateText + HTML_ESCAPE_SUFFIX);
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		delegate.closeTemplateSource(templateSource);
	}


}
