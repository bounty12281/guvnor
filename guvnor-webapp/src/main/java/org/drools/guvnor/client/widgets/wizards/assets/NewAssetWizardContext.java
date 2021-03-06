/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.guvnor.client.widgets.wizards.assets;

import org.drools.guvnor.client.widgets.wizards.WizardContext;
import org.drools.guvnor.client.widgets.wizards.WizardPlace;
import org.drools.ide.common.client.modeldriven.dt52.GuidedDecisionTable52.TableFormat;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * A container for the details required to create a new Asset on the repository
 */
public class NewAssetWizardContext
    implements
    WizardContext {

    private final String      assetName;
    private final String      packageName;
    private final TableFormat tableFormat;
    private final String      packageUUID;
    private final String      format;
    private final String      description;
    private final String      initialCategory;

    public NewAssetWizardContext(String assetName,
                                 String packageName,
                                 String packageUUID,
                                 TableFormat tableFormat,
                                 String description,
                                 String initialCategory,
                                 String format) {
        this.assetName = assetName;
        this.packageName = packageName;
        this.packageUUID = packageUUID;
        this.tableFormat = tableFormat;
        this.description = description;
        this.initialCategory = initialCategory;
        this.format = format;
    }

    public String getAssetName() {
        return this.assetName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPackageUUID() {
        return this.packageUUID;
    }

    public TableFormat getTableFormat() {
        return this.tableFormat;
    }

    public String getFormat() {
        return this.format;
    }

    public String getDescription() {
        return this.description;
    }

    public String getInitialCategory() {
        return this.initialCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = hash + 31 * (assetName == null ? 0 : assetName.hashCode());
        hash = hash + 31 * (packageName == null ? 0 : packageName.hashCode());
        hash = hash + 31 * (packageUUID == null ? 0 : packageUUID.hashCode());
        hash = hash + 31 * tableFormat.hashCode();
        hash = hash + 31 * (format == null ? 0 : format.hashCode());
        hash = hash + 31 * (description == null ? 0 : description.hashCode());
        hash = hash + 31 * (initialCategory == null ? 0 : initialCategory.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if ( !(o instanceof NewAssetWizardContext) ) {
            return false;
        }
        NewAssetWizardContext that = (NewAssetWizardContext) o;

        if ( assetName != null ? !assetName.equals( that.assetName ) : that.assetName != null ) return false;
        if ( packageName != null ? !packageName.equals( that.packageName ) : that.packageName != null ) return false;
        if ( packageUUID != null ? !packageUUID.equals( that.packageUUID ) : that.packageUUID != null ) return false;
        if ( !tableFormat.equals( that.tableFormat ) ) return false;
        if ( format != null ? !assetName.equals( that.format ) : that.format != null ) return false;
        if ( description != null ? !description.equals( that.description ) : that.description != null ) return false;
        if ( initialCategory != null ? !initialCategory.equals( that.initialCategory ) : that.initialCategory != null ) return false;
        return true;
    }

    public static class Tokenizer
        implements
        PlaceTokenizer<WizardPlace<NewAssetWizardContext>> {

        public String getToken(WizardPlace<NewAssetWizardContext> place) {
            StringBuilder sb = new StringBuilder();
            sb.append( nullSafe( place.getContext().getAssetName() ) );
            sb.append( "|" );
            sb.append( nullSafe( place.getContext().getPackageName() ) );
            sb.append( "|" );
            sb.append( nullSafe( place.getContext().getPackageUUID() ) );
            sb.append( "|" );
            sb.append( place.getContext().getTableFormat().toString() );
            sb.append( "|" );
            sb.append( nullSafe( place.getContext().getDescription() ) );
            sb.append( "|" );
            sb.append( nullSafe( place.getContext().getInitialCategory() ) );
            sb.append( "|" );
            sb.append( nullSafe( place.getContext().getFormat() ) );
            return sb.toString();
        }

        private String nullSafe(String s) {
            return s == null ? "" : s;
        }

        public WizardPlace<NewAssetWizardContext> getPlace(String token) {
            String[] parts = token.split( "\\|" );
            if ( parts.length != 7 ) {
                throw new IllegalArgumentException( "WizardPlace token is not structured correctly. Expecting 'assetName|packageName|packageUUID|tableFormat|format|description|initialCategory'" );
            }
            NewAssetWizardContext config = new NewAssetWizardContext( parts[0],
                                                                      parts[1],
                                                                      parts[2],
                                                                      TableFormat.valueOf( parts[3] ),
                                                                      parts[4],
                                                                      parts[5],
                                                                      parts[6] );
            return new WizardPlace<NewAssetWizardContext>( config );
        }
    }

}
