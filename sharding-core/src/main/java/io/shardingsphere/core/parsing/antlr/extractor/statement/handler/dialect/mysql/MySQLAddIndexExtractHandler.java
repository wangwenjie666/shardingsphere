/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antlr.extractor.statement.handler.dialect.mysql;

import java.util.Collection;

import org.antlr.v4.runtime.ParserRuleContext;

import com.google.common.base.Optional;

import io.shardingsphere.core.parsing.antlr.extractor.statement.handler.ASTExtractHandler;
import io.shardingsphere.core.parsing.antlr.extractor.statement.handler.RuleName;
import io.shardingsphere.core.parsing.antlr.extractor.statement.handler.result.ExtractResult;
import io.shardingsphere.core.parsing.antlr.extractor.statement.handler.result.SQLTokenExtractResult;
import io.shardingsphere.core.parsing.antlr.extractor.statement.util.ASTUtils;
import io.shardingsphere.core.parsing.parser.token.IndexToken;
import io.shardingsphere.core.util.SQLUtil;

/**
 * Add index extract handler for MySQL.
 * 
 * @author duhongjun
 */
public final class MySQLAddIndexExtractHandler implements ASTExtractHandler {

    @Override
    public ExtractResult extract(ParserRuleContext ancestorNode) {
        Collection<ParserRuleContext> result = ASTUtils.getAllDescendantNodes(ancestorNode, RuleName.ADD_INDEX);
        if(result.isEmpty()) {
            return null;
        }
        SQLTokenExtractResult extractResult = new SQLTokenExtractResult();
        for (ParserRuleContext each : result) {
            Optional<ParserRuleContext> indexNameNode = ASTUtils.findFirstChildNode(each, RuleName.INDEX_NAME);
            if (indexNameNode.isPresent()) {
                extractResult.getSqlTokens().add(
                        new IndexToken(indexNameNode.get().getStop().getStartIndex(), SQLUtil.getNameWithoutSchema(indexNameNode.get().getText()), null));
            }
        }
        return extractResult;
    }
}
