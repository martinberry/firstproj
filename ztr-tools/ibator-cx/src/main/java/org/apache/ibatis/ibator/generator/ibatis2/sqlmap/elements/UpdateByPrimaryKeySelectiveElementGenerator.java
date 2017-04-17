/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.ibator.generator.ibatis2.sqlmap.elements;

import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.TextElement;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.generator.ibatis2.Ibatis2FormattingUtilities;

/**
 * 
 * @author Jeff Butler
 *
 */
public class UpdateByPrimaryKeySelectiveElementGenerator extends
        AbstractXmlElementGenerator {

    public UpdateByPrimaryKeySelectiveElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id",  introspectedTable.getIbatis2SqlMapNamespace() + "."+introspectedTable.getUpdateByPrimaryKeySelectiveStatementId())); //$NON-NLS-1$

        String parameterType;
        
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = introspectedTable.getRecordWithBLOBsType();
        } else {
            parameterType = introspectedTable.getBaseRecordType();
        }
        
        answer.addAttribute(new Attribute("parameterClass", //$NON-NLS-1$
                parameterType));

        ibatorContext.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("dynamic"); //$NON-NLS-1$
        dynamicElement.addAttribute(new Attribute("prepend", "set")); //$NON-NLS-1$ //$NON-NLS-2$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("isNotNull"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("prepend", ",")); //$NON-NLS-1$ //$NON-NLS-2$
            isNotNullElement.addAttribute(new Attribute("property", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append("<![CDATA[ ");
            sb.append(Ibatis2FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(Ibatis2FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append(" ]]>");
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        boolean and = false;
        answer.addElement(new TextElement("<![CDATA["));
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(Ibatis2FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(Ibatis2FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(new TextElement("]]>"));
        
        if (ibatorContext.getPlugins().sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}