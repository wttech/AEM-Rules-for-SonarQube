/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ConstantsChecker {

    private static final Map<String, String> CONSTANTS;

    private static final Map<String, String> ANNOTATION_CONSTANTS;

    static {
        CONSTANTS = ImmutableMap.<String, String>builder()

            // interface com.day.cq.commons.jcr.JcrConstants
            .put("jcr:autoCreated", "JCR_AUTOCREATED from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:baseVersion", "JCR_BASEVERSION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:child", "JCR_CHILD from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:childNodeDefinition", "JCR_CHILDNODEDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:content", "JCR_CONTENT from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:data", "JCR_DATA from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:defaultPrimaryType", "JCR_DEFAULTPRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:defaultValues", "JCR_DEFAULTVALUES from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:encoding", "JCR_ENCODING from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:frozenMixinTypes", "JCR_FROZENMIXINTYPES from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:frozenNode", "JCR_FROZENNODE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:frozenPrimaryType", "JCR_FROZENPRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:frozenUuid", "JCR_FROZENUUID from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:hasOrderableChildNodes", "JCR_HASORDERABLECHILDNODES from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:isCheckedOut", "JCR_ISCHECKEDOUT from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:isMixin", "JCR_ISMIXIN from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:language", "JCR_LANGUAGE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:lockIsDeep", "JCR_LOCKISDEEP from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:lockOwner", "JCR_LOCKOWNER from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:mandatory", "JCR_MANDATORY from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:mergeFailed", "JCR_MERGEFAILED from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:mimeType", "JCR_MIMETYPE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:mixinTypes", "JCR_MIXINTYPES from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:multiple", "JCR_MULTIPLE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:name", "JCR_NAME from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:nodeTypeName", "JCR_NODETYPENAME from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:onParentVersion", "JCR_ONPARENTVERSION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:predecessors", "JCR_PREDECESSORS from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:primaryItemName", "JCR_PRIMARYITEMNAME from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:primaryType", "JCR_PRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:propertyDefinition", "JCR_PROPERTYDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:protected", "JCR_PROTECTED from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:requiredPrimaryTypes", "JCR_REQUIREDPRIMARYTYPES from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:requiredType", "JCR_REQUIREDTYPE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:rootVersion", "JCR_ROOTVERSION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:sameNameSiblings", "JCR_SAMENAMESIBLINGS from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:statement", "JCR_STATEMENT from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:successors", "JCR_SUCCESSORS from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:supertypes", "JCR_SUPERTYPES from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:system", "JCR_SYSTEM from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:uuid", "JCR_UUID from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:valueConstraints", "JCR_VALUECONSTRAINTS from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:versionHistory", "JCR_VERSIONHISTORY from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:versionLabels", "JCR_VERSIONLABELS from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:versionStorage", "JCR_VERSIONSTORAGE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:versionableUuid", "JCR_VERSIONABLEUUID from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:path", "JCR_PATH from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:score", "JCR_SCORE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("mix:lockable", "MIX_LOCKABLE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("mix:referenceable", "MIX_REFERENCEABLE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("mix:versionable", "MIX_VERSIONABLE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:base", "NT_BASE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:childNodeDefinition", "NT_CHILDNODEDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:file", "NT_FILE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:folder", "NT_FOLDER from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:frozenNode", "NT_FROZENNODE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:hierarchyNode", "NT_HIERARCHYNODE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:linkedFile", "NT_LINKEDFILE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:nodeType", "NT_NODETYPE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:propertyDefinition", "NT_PROPERTYDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:query", "NT_QUERY from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:resource", "NT_RESOURCE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:unstructured", "NT_UNSTRUCTURED from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:version", "NT_VERSION from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:versionHistory", "NT_VERSIONHISTORY from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:versionLabels", "NT_VERSIONLABELS from interface com.day.cq.commons.jcr.JcrConstants")
            .put("nt:versionedChild", "NT_VERSIONEDCHILD from interface com.day.cq.commons.jcr.JcrConstants")
            .put("jcr:title", "JCR_TITLE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("mix:title", "MIX_TITLE from interface com.day.cq.commons.jcr.JcrConstants")
            .put("mix:created", "MIX_CREATED from interface com.day.cq.commons.jcr.JcrConstants")
            .put("mix:lastModified", "MIX_LAST_MODIFIED from interface com.day.cq.commons.jcr.JcrConstants")

            // interface com.day.cq.dam.api.DamConstants
            .put("dam:Asset", "NT_DAM_ASSET from interface com.day.cq.dam.api.DamConstants")
            .put("dam:AssetContent", "NT_DAM_ASSETCONTENT from interface com.day.cq.dam.api.DamConstants")
            .put("dam:extracted", "PN_EXTRACTED from interface com.day.cq.dam.api.DamConstants")
            .put("dam:sha1", "PN_SHA1 from interface com.day.cq.dam.api.DamConstants")
            .put("dam:size", "DAM_SIZE from interface com.day.cq.dam.api.DamConstants")
            .put("cq:versionCreator", "PN_VERSION_CREATOR from interface com.day.cq.dam.api.DamConstants")
            .put("dc:contributor", "DC_CONTRIBUTOR from interface com.day.cq.dam.api.DamConstants")
            .put("dc:coverage", "DC_COVERAGE from interface com.day.cq.dam.api.DamConstants")
            .put("dc:creator", "DC_CREATOR from interface com.day.cq.dam.api.DamConstants")
            .put("dc:date", "DC_DATE from interface com.day.cq.dam.api.DamConstants")
            .put("dc:description", "DC_DESCRIPTION from interface com.day.cq.dam.api.DamConstants")
            .put("dc:extent", "DC_EXTENT from interface com.day.cq.dam.api.DamConstants")
            .put("dc:format", "DC_FORMAT from interface com.day.cq.dam.api.DamConstants")
            .put("dc:identifier", "DC_IDENTIFIER from interface com.day.cq.dam.api.DamConstants")
            .put("dc:language", "DC_LANGUAGE from interface com.day.cq.dam.api.DamConstants")
            .put("dc:modified", "DC_MODIFIED from interface com.day.cq.dam.api.DamConstants")
            .put("dc:publisher", "DC_PUBLISHER from interface com.day.cq.dam.api.DamConstants")
            .put("dc:relation", "DC_RELATION from interface com.day.cq.dam.api.DamConstants")
            .put("dc:rights", "DC_RIGHTS from interface com.day.cq.dam.api.DamConstants")
            .put("dc:subject", "DC_SUBJECT from interface com.day.cq.dam.api.DamConstants")
            .put("dc:title", "DC_TITLE from interface com.day.cq.dam.api.DamConstants")
            .put("dc:type", "DC_TYPE from interface com.day.cq.dam.api.DamConstants")
            .put("cq5dam.thumbnail", "PREFIX_ASSET_THUMBNAIL from interface com.day.cq.dam.api.DamConstants")
            .put("exif:PixelXDimension", "EXIF_PIXELXDIMENSION from interface com.day.cq.dam.api.DamConstants")
            .put("exif:PixelYDimension", "EXIF_PIXELYDIMENSION from interface com.day.cq.dam.api.DamConstants")
            .put("tiff:ImageLength", "TIFF_IMAGELENGTH from interface com.day.cq.dam.api.DamConstants")
            .put("tiff:ImageWidth", "TIFF_IMAGEWIDTH from interface com.day.cq.dam.api.DamConstants")
            .put("assetExpired", "ACTIVITY_TYPE_ASSET_EXPIRED from interface com.day.cq.dam.api.DamConstants")
            .put("assetExpiring", "ACTIVITY_TYPE_ASSET_EXPIRING from interface com.day.cq.dam.api.DamConstants")
            .put("dam:lastPostExpirationRun", "LAST_EXPIRY_NOTIFICATION_PROPNAME from interface com.day.cq.dam.api.DamConstants")
            .put("dam/collection", "COLLECTION_SLING_RES_TYPE from interface com.day.cq.dam.api.DamConstants")
            .put("dam/smartcollection", "SMART_COLLECTION_SLING_RES_TYPE from interface com.day.cq.dam.api.DamConstants")
            .put("dam/content/schemaeditors/forms", "SCHEMA_EDITOR_FORMS_BASE_DIR from interface com.day.cq.dam.api.DamConstants")
            .put("processingProfile", "PROCESSING_PROFILE from interface com.day.cq.dam.api.DamConstants")
            .put("metadataProfile", "METADATA_PROFILE from interface com.day.cq.dam.api.DamConstants")
            .put("videoProfile", "VIDEO_PROFILE from interface com.day.cq.dam.api.DamConstants")
            .put("imageProfile", "IMAGE_PROFILE from interface com.day.cq.dam.api.DamConstants")
            .put("folderThumbnail", "THUMBNAIL_NODE from interface com.day.cq.dam.api.DamConstants")
            .put("downloadUrl", "DOWNLOAD_URL from interface com.day.cq.dam.api.DamConstants")

            // interface com.day.cq.wcm.api.NameConstants
            .put("cq:Page", "NT_PAGE from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:PseudoPage", "NT_PSEUDO_PAGE from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:Template", "NT_TEMPLATE from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:Component", "NT_COMPONENT from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:EditConfig", "NT_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants")
            .put("dialog", "NN_DIALOG from interface com.day.cq.wcm.api.NameConstants")
            .put("dialogPath", "PN_DIALOG_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("design_dialog", "NN_DESIGN_DIALOG from interface com.day.cq.wcm.api.NameConstants")
            .put("designDialogPath", "PN_DESIGN_DIALOG_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:editConfig", "NN_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:childEditConfig", "NN_CHILD_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants")
            .put("icon.png", "NN_ICON_PNG from interface com.day.cq.wcm.api.NameConstants")
            .put("thumbnail.png", "NN_THUMBNAIL_PNG from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:cellName", "PN_CELL_NAME from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:isContainer", "PN_IS_CONTAINER from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:noDecoration", "PN_NO_DECORATION from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:htmlTag", "NN_HTML_TAG from interface com.day.cq.wcm.api.NameConstants")
            .put("allowedPaths", "PN_ALLOWED_PATHS from interface com.day.cq.wcm.api.NameConstants")
            .put("allowedChildren", "PN_ALLOWED_CHILDREN from interface com.day.cq.wcm.api.NameConstants")
            .put("allowedParents", "PN_ALLOWED_PARENTS from interface com.day.cq.wcm.api.NameConstants")
            .put("componentGroup", "PN_COMPONENT_GROUP from interface com.day.cq.wcm.api.NameConstants")
            .put("sitePath", "PN_SITE_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:templatePath", "PN_TEMPLATE_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:template", "NN_TEMPLATE from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:tagName", "PN_TAG_NAME from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:infoProviders", "NN_INFO_PROVIDERS from interface com.day.cq.wcm.api.NameConstants")
            .put("className", "PN_CLASS_NAME from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:disableTargeting", "PN_DISABLE_TARGETING from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:layout", "PN_LAYOUT from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:dialogMode", "PN_DIALOG_MODE from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:inplaceEditing", "NN_INPLACE_EDITING from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:emptyText", "PN_EMPTY_TEXT from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:actions", "PN_ACTIONS from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:actionConfigs", "NN_ACTION_CONFIGS from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:formParameters", "NN_FORM_PARAMETERS from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:dropTargets", "NN_DROP_TARGETS from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:listeners", "NN_LISTENERS from interface com.day.cq.wcm.api.NameConstants")
            .put("propertyName", "PN_DT_NAME from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:inherit", "PN_INHERIT from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:designPath", "PN_DESIGN_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:parentPath", "PN_PARENT_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:childrenOrder", "PN_CHILDREN_ORDER from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:siblingOrder", "PN_SIBLING_ORDER from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:versionComment", "PN_VERSION_COMMENT from interface com.day.cq.wcm.api.NameConstants")
            .put("onTime", "PN_ON_TIME from interface com.day.cq.wcm.api.NameConstants")
            .put("offTime", "PN_OFF_TIME from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:lastModified", "PN_PAGE_LAST_MOD from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:lastModifiedBy", "PN_PAGE_LAST_MOD_BY from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:defaultView", "PN_DEFAULT_VIEW from interface com.day.cq.wcm.api.NameConstants")
            .put("sling:vanityPath", "PN_SLING_VANITY_PATH from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:allowedTemplates", "PN_ALLOWED_TEMPLATES from interface com.day.cq.wcm.api.NameConstants")

            // interface com.day.cq.wcm.webservicesupport.ConfigurationConstants
            .put("cq/cloudserviceconfigs/components/servicepage", "RT_SERVICE from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants")
            .put("cq/cloudserviceconfigs/components/configpage", "RT_CONFIGURATION from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants")
            .put("cq:cloudserviceconfigs", "PN_CONFIGURATIONS from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants")

            // class org.apache.sling.jcr.resource.JcrResourceConstants
            .put("http://sling.apache.org/jcr/sling/1.0", "SLING_NAMESPACE_URI from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("sling:resourceType", "SLING_RESOURCE_TYPE_PROPERTY from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("sling:resourceSuperType", "SLING_RESOURCE_SUPER_TYPE_PROPERTY from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("user.jcr.credentials", "AUTHENTICATION_INFO_CREDENTIALS from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("user.jcr.workspace", "AUTHENTICATION_INFO_WORKSPACE from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("user.jcr.session", "AUTHENTICATION_INFO_SESSION from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("sling:Folder", "NT_SLING_FOLDER from class org.apache.sling.jcr.resource.JcrResourceConstants")
            .put("sling:OrderedFolder", "NT_SLING_ORDERED_FOLDER from class org.apache.sling.jcr.resource.JcrResourceConstants")

            // interface com.day.cq.tagging.TagConstants
            .put("cq:Tag", "NT_TAG from interface com.day.cq.tagging.TagConstants")
            .put("cq:Taggable", "NT_TAGGABLE from interface com.day.cq.tagging.TagConstants")
            .put("cq:movedTo", "PN_MOVED_TO from interface com.day.cq.tagging.TagConstants")
            .put("cq:backlinks", "PN_BACKLINKS from interface com.day.cq.tagging.TagConstants")

            // interface com.day.cq.replication.ReplicationStatus
            .put("cq:ReplicationStatus", "NODE_TYPE from interface com.day.cq.replication.ReplicationStatus")

            // interface org.apache.jackrabbit.vault.packaging.JcrPackage
            .put("application/zip", "MIME_TYPE from interface org.apache.jackrabbit.vault.packaging.JcrPackage")
            .put("vlt:definition", "NN_VLT_DEFINITION from interface org.apache.jackrabbit.vault.packaging.JcrPackage")
            .put("vlt:Package", "NT_VLT_PACKAGE from interface org.apache.jackrabbit.vault.packaging.JcrPackage")
            .put("vlt:PackageDefinition", "NT_VLT_PACKAGE_DEFINITION from interface org.apache.jackrabbit.vault.packaging.JcrPackage")

            // interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition
            .put("acHandling", "PN_AC_HANDLING from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("buildCount", "PN_BUILD_COUNT from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("cndPattern", "PN_CND_PATTERN from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("dependencies", "PN_DEPENDENCIES from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("noIntermediateSaves", "PN_DISABLE_INTERMEDIATE_SAVE from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("lastUnpacked", "PN_LAST_UNPACKED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("lastUnpackedBy", "PN_LAST_UNPACKED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("lastUnwrapped", "PN_LAST_UNWRAPPED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("lastUnwrappedBy", "PN_LAST_UNWRAPPED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("lastWrapped", "PN_LAST_WRAPPED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("lastWrappedBy", "PN_LAST_WRAPPED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("requiresRestart", "PN_REQUIRES_RESTART from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("requiresRoot", "PN_REQUIRES_ROOT from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("subPackages", "PN_SUB_PACKAGES from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")

            // interface org.apache.sling.api.resource.ResourceResolverFactory
            .put("user.password", "PASSWORD from interface org.apache.sling.api.resource.ResourceResolverFactory")
            .put("sling.service.subservice", "SUBSERVICE from interface org.apache.sling.api.resource.ResourceResolverFactory")
            .put("user.name", "USER from interface org.apache.sling.api.resource.ResourceResolverFactory")
            .put("user.impersonation", "USER_IMPERSONATION from interface org.apache.sling.api.resource.ResourceResolverFactory")

            // class com.adobe.granite.workflow.event.WorkflowEvent
            .put("Delagatee", "DELEGATEE from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("EventType", "EVENT_TYPE from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("fromNodeName", "FROM_NODE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("JobFailed", "JOB_FAILED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("ModelDeleted", "MODEL_DELETED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("ModelDeployed", "MODEL_DEPLOYED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("NodeTransition", "NODE_TRANSITION_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("ParentWorkflowId", "PARENT_WORKFLOW_ID from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("ProcessTimeout", "PROCESS_TIMEOUT_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("ResourceCollectionModified", "RESOURCE_COLLECTION_MODIFIED from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("TimeStamp", "TIME_STAMP from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("toNodeName", "TO_NODE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("User", "USER from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("VariableName", "VARIABLE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("VariableUpdate", "VARIABLE_UPDATE_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("VariableValue", "VARIABLE_VALUE from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("Workdata", "WORK_DATA from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("Workitem", "WORK_ITEM from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowAborted", "WORKFLOW_ABORTED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowCompleted", "WORKFLOW_COMPLETED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowInstanceId", "WORKFLOW_INSTANCE_ID from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowName", "WORKFLOW_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowNode", "WORKFLOW_NODE from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowResumed", "WORKFLOW_RESUMED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowStarted", "WORKFLOW_STARTED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowSuspended", "WORKFLOW_SUSPENDED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkflowVersion", "WORKFLOW_VERSION from class com.adobe.granite.workflow.event.WorkflowEvent")
            .put("WorkItemDelegated", "WORKITEM_DELEGATION_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")

            // mix
            .put("cq:lastReplicated",
                "NODE_PROPERTY_LAST_REPLICATED from interface com.day.cq.replication.ReplicationStatus"
                    + " or constant PN_PAGE_LAST_REPLICATED from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:lastReplicatedBy",
                "NODE_PROPERTY_LAST_REPLICATED_BY from interface com.day.cq.replication.ReplicationStatus"
                    + " or constant PN_PAGE_LAST_REPLICATED_BY from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:lastReplicationAction",
                "NODE_PROPERTY_LAST_REPLICATION_ACTION from interface com.day.cq.replication.ReplicationStatus"
                    + " or constant PN_PAGE_LAST_REPLICATION_ACTION from interface com.day.cq.wcm.api.NameConstants")
            .put("cq:tags", "PN_TAGS from interface com.day.cq.tagging.TagConstants.PN_TAGS or interface com.day.cq.wcm.api.NameConstants")
            .put("cq:name", "PN_NAME from interface com.day.cq.dam.api.DamConstants.PN_NAME or interface com.day.cq.wcm.api.NameConstants")
            .put("jcr:created",
                "JCR_CREATED from interface com.day.cq.commons.jcr.JcrConstants"
                    + " or constant PN_CREATED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("jcr:createdBy",
                "JCR_CREATED_BY from interface com.day.cq.commons.jcr.JcrConstants"
                    + " or constant PN_CREATED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("jcr:description",
                "JCR_DESCRIPTION from interface com.day.cq.commons.jcr.JcrConstants"
                    + " or constant PN_DESCRIPTION from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("jcr:lastModified",
                "JCR_LASTMODIFIED from interface com.day.cq.commons.jcr.JcrConstants"
                    + " or constant PN_LASTMODIFIED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .put("jcr:lastModifiedBy", "JCR_LAST_MODIFIED_BY from interface com.day.cq.commons.jcr.JcrConstants"
                + " or constant PN_LASTMODIFIED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
            .build();

        ANNOTATION_CONSTANTS = ImmutableMap.<String, String>builder()

            // class org.apache.sling.api.SlingConstants
            .put("org.apache.sling.api.include.servlet", "ATTR_REQUEST_SERVLET from class org.apache.sling.api.SlingConstants")
            .put("org.apache.sling.api.include.resource", "ATTR_REQUEST_CONTENT from class org.apache.sling.api.SlingConstants")
            .put("org.apache.sling.api.include.request_path_info", "ATTR_REQUEST_PATH_INFO from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.include.request_uri", "ATTR_INCLUDE_REQUEST_URI from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.include.context_path", "ATTR_INCLUDE_CONTEXT_PATH from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.include.servlet_path", "ATTR_INCLUDE_SERVLET_PATH from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.include.path_info", "ATTR_INCLUDE_PATH_INFO from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.include.query_string", "ATTR_INCLUDE_QUERY_STRING from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.error.exception", "ERROR_EXCEPTION from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.error.exception_type", "ERROR_EXCEPTION_TYPE from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.error.message", "ERROR_MESSAGE from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.error.request_uri", "ERROR_REQUEST_URI from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.error.servlet_name", "ERROR_SERVLET_NAME from class org.apache.sling.api.SlingConstants")
            .put("javax.servlet.error.status_code", "ERROR_STATUS from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/resource/Resource/ADDED", "TOPIC_RESOURCE_ADDED from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/resource/Resource/REMOVED", "TOPIC_RESOURCE_REMOVED from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/resource/Resource/CHANGED", "TOPIC_RESOURCE_CHANGED from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/resource/ResourceProvider/ADDED", "TOPIC_RESOURCE_PROVIDER_ADDED from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/resource/ResourceProvider/REMOVED", "TOPIC_RESOURCE_PROVIDER_REMOVED from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/resource/ResourceResolverMapping/CHANGED",
                "TOPIC_RESOURCE_RESOLVER_MAPPING_CHANGED from class org.apache.sling.api.SlingConstants")
            .put("path", "PROPERTY_PATH from class org.apache.sling.api.SlingConstants")
            .put("userid", "PROPERTY_USERID from class org.apache.sling.api.SlingConstants")
            .put("resourceType", "PROPERTY_RESOURCE_TYPE from class org.apache.sling.api.SlingConstants")
            .put("resourceSuperType", "PROPERTY_RESOURCE_SUPER_TYPE from class org.apache.sling.api.SlingConstants")
            .put("resourceChangedAttributes", "PROPERTY_CHANGED_ATTRIBUTES from class org.apache.sling.api.SlingConstants")
            .put("resourceAddedAttributes", "PROPERTY_ADDED_ATTRIBUTES from class org.apache.sling.api.SlingConstants")
            .put("resourceRemovedAttributes", "PROPERTY_REMOVED_ATTRIBUTES from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/adapter/AdapterFactory/ADDED", "TOPIC_ADAPTER_FACTORY_ADDED from class org.apache.sling.api.SlingConstants")
            .put("org/apache/sling/api/adapter/AdapterFactory/REMOVED", "TOPIC_ADAPTER_FACTORY_REMOVED from class org.apache.sling.api.SlingConstants")
            .put("adaptables", "PROPERTY_ADAPTABLE_CLASSES from class org.apache.sling.api.SlingConstants")
            .put("adapters", "PROPERTY_ADAPTER_CLASSES from class org.apache.sling.api.SlingConstants")
            .put("sling.core.current.servletName", "SLING_CURRENT_SERVLET_NAME from class org.apache.sling.api.SlingConstants")

            // class org.apache.sling.api.servlets.HttpConstants
            .put("OPTIONS", "METHOD_OPTIONS from class org.apache.sling.api.servlets.HttpConstants")
            .put("GET", "METHOD_GET from class org.apache.sling.api.servlets.HttpConstants")
            .put("HEAD", "METHOD_HEAD from class org.apache.sling.api.servlets.HttpConstants")
            .put("POST", "METHOD_POST from class org.apache.sling.api.servlets.HttpConstants")
            .put("PUT", "METHOD_PUT from class org.apache.sling.api.servlets.HttpConstants")
            .put("DELETE", "METHOD_DELETE from class org.apache.sling.api.servlets.HttpConstants")
            .put("TRACE", "METHOD_TRACE from class org.apache.sling.api.servlets.HttpConstants")
            .put("CONNECT", "METHOD_CONNECT from class org.apache.sling.api.servlets.HttpConstants")
            .put("Accept", "HEADER_ACCEPT from class org.apache.sling.api.servlets.HttpConstants")
            .put("ETag", "HEADER_ETAG from class org.apache.sling.api.servlets.HttpConstants")
            .put("If-Match", "HEADER_IF_MATCH from class org.apache.sling.api.servlets.HttpConstants")
            .put("If-Modified-Since", "HEADER_IF_MODIFIED_SINCE from class org.apache.sling.api.servlets.HttpConstants")
            .put("Last-Modified", "HEADER_LAST_MODIFIED from class org.apache.sling.api.servlets.HttpConstants")

            // interface org.osgi.framework.Constants
            .put("System Bundle", "SYSTEM_BUNDLE_LOCATION from interface org.osgi.framework.Constants")
            .put("system.bundle", "SYSTEM_BUNDLE_SYMBOLICNAME from interface org.osgi.framework.Constants")
            .put("Bundle-Category", "BUNDLE_CATEGORY from interface org.osgi.framework.Constants")
            .put("Bundle-ClassPath", "BUNDLE_CLASSPATH from interface org.osgi.framework.Constants")
            .put("Bundle-Copyright", "BUNDLE_COPYRIGHT from interface org.osgi.framework.Constants")
            .put("Bundle-Description", "BUNDLE_DESCRIPTION from interface org.osgi.framework.Constants")
            .put("Bundle-Name", "BUNDLE_NAME from interface org.osgi.framework.Constants")
            .put("Bundle-NativeCode", "BUNDLE_NATIVECODE from interface org.osgi.framework.Constants")
            .put("Export-Package", "EXPORT_PACKAGE from interface org.osgi.framework.Constants")
            .put("Export-Service", "EXPORT_SERVICE from interface org.osgi.framework.Constants")
            .put("Import-Package", "IMPORT_PACKAGE from interface org.osgi.framework.Constants")
            .put("DynamicImport-Package", "DYNAMICIMPORT_PACKAGE from interface org.osgi.framework.Constants")
            .put("Import-Service", "IMPORT_SERVICE from interface org.osgi.framework.Constants")
            .put("Bundle-Vendor", "BUNDLE_VENDOR from interface org.osgi.framework.Constants")
            .put("Bundle-Version", "BUNDLE_VERSION from interface org.osgi.framework.Constants")
            .put("Bundle-DocURL", "BUNDLE_DOCURL from interface org.osgi.framework.Constants")
            .put("Bundle-ContactAddress", "BUNDLE_CONTACTADDRESS from interface org.osgi.framework.Constants")
            .put("Bundle-Activator", "BUNDLE_ACTIVATOR from interface org.osgi.framework.Constants")
            .put("Bundle-UpdateLocation", "BUNDLE_UPDATELOCATION from interface org.osgi.framework.Constants")
            .put("specification-version", "PACKAGE_SPECIFICATION_VERSION from interface org.osgi.framework.Constants")
            .put("processor", "BUNDLE_NATIVECODE_PROCESSOR from interface org.osgi.framework.Constants")
            .put("osname", "BUNDLE_NATIVECODE_OSNAME from interface org.osgi.framework.Constants")
            .put("osversion", "BUNDLE_NATIVECODE_OSVERSION from interface org.osgi.framework.Constants")
            .put("language", "BUNDLE_NATIVECODE_LANGUAGE from interface org.osgi.framework.Constants")
            .put("Bundle-RequiredExecutionEnvironment", "BUNDLE_REQUIREDEXECUTIONENVIRONMENT from interface org.osgi.framework.Constants")
            .put("Bundle-SymbolicName", "BUNDLE_SYMBOLICNAME from interface org.osgi.framework.Constants")
            .put("singleton", "SINGLETON_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("fragment-attachment", "FRAGMENT_ATTACHMENT_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("always", "FRAGMENT_ATTACHMENT_ALWAYS from interface org.osgi.framework.Constants")
            .put("resolve-time", "FRAGMENT_ATTACHMENT_RESOLVETIME from interface org.osgi.framework.Constants")
            .put("never", "FRAGMENT_ATTACHMENT_NEVER from interface org.osgi.framework.Constants")
            .put("Bundle-Localization", "BUNDLE_LOCALIZATION from interface org.osgi.framework.Constants")
            .put("OSGI-INF/l10n/bundle", "BUNDLE_LOCALIZATION_DEFAULT_BASENAME from interface org.osgi.framework.Constants")
            .put("Require-Bundle", "REQUIRE_BUNDLE from interface org.osgi.framework.Constants")
            .put("bundle-version", "BUNDLE_VERSION_ATTRIBUTE from interface org.osgi.framework.Constants")
            .put("Fragment-Host", "FRAGMENT_HOST from interface org.osgi.framework.Constants")
            .put("selection-filter", "SELECTION_FILTER_ATTRIBUTE from interface org.osgi.framework.Constants")
            .put("Bundle-ManifestVersion", "BUNDLE_MANIFESTVERSION from interface org.osgi.framework.Constants")
            .put("version", "VERSION_ATTRIBUTE from interface org.osgi.framework.Constants")
            .put("bundle-symbolic-name", "BUNDLE_SYMBOLICNAME_ATTRIBUTE from interface org.osgi.framework.Constants")
            .put("resolution", "RESOLUTION_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("optional", "RESOLUTION_OPTIONAL from interface org.osgi.framework.Constants")
            .put("uses", "USES_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("include", "INCLUDE_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("exclude", "EXCLUDE_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("mandatory", "MANDATORY_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("visibility", "VISIBILITY_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("private", "VISIBILITY_PRIVATE from interface org.osgi.framework.Constants")
            .put("reexport", "VISIBILITY_REEXPORT from interface org.osgi.framework.Constants")
            .put("extension", "EXTENSION_DIRECTIVE from interface org.osgi.framework.Constants")
            .put("framework", "EXTENSION_FRAMEWORK from interface org.osgi.framework.Constants")
            .put("bootclasspath", "EXTENSION_BOOTCLASSPATH from interface org.osgi.framework.Constants")
            .put("Bundle-ActivationPolicy", "BUNDLE_ACTIVATIONPOLICY from interface org.osgi.framework.Constants")
            .put("lazy", "ACTIVATION_LAZY from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.version", "FRAMEWORK_VERSION from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.vendor", "FRAMEWORK_VENDOR from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.language", "FRAMEWORK_LANGUAGE from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.os.name", "FRAMEWORK_OS_NAME from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.os.version", "FRAMEWORK_OS_VERSION from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.processor", "FRAMEWORK_PROCESSOR from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.executionenvironment", "FRAMEWORK_EXECUTIONENVIRONMENT from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.bootdelegation", "FRAMEWORK_BOOTDELEGATION from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.system.packages", "FRAMEWORK_SYSTEMPACKAGES from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.system.packages.extra", "FRAMEWORK_SYSTEMPACKAGES_EXTRA from interface org.osgi.framework.Constants")
            .put("org.osgi.supports.framework.extension", "SUPPORTS_FRAMEWORK_EXTENSION from interface org.osgi.framework.Constants")
            .put("org.osgi.supports.bootclasspath.extension", "SUPPORTS_BOOTCLASSPATH_EXTENSION from interface org.osgi.framework.Constants")
            .put("org.osgi.supports.framework.fragment", "SUPPORTS_FRAMEWORK_FRAGMENT from interface org.osgi.framework.Constants")
            .put("org.osgi.supports.framework.requirebundle", "SUPPORTS_FRAMEWORK_REQUIREBUNDLE from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.security", "FRAMEWORK_SECURITY from interface org.osgi.framework.Constants")
            .put("osgi", "FRAMEWORK_SECURITY_OSGI from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.storage", "FRAMEWORK_STORAGE from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.storage.clean", "FRAMEWORK_STORAGE_CLEAN from interface org.osgi.framework.Constants")
            .put("onFirstInit", "FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.library.extensions", "FRAMEWORK_LIBRARY_EXTENSIONS from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.command.execpermission", "FRAMEWORK_EXECPERMISSION from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.trust.repositories", "FRAMEWORK_TRUST_REPOSITORIES from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.windowsystem", "FRAMEWORK_WINDOWSYSTEM from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.startlevel.beginning", "FRAMEWORK_BEGINNING_STARTLEVEL from interface org.osgi.framework.Constants")
            .put("org.osgi.framework.bundle.parent", "FRAMEWORK_BUNDLE_PARENT from interface org.osgi.framework.Constants")
            .put("service.id", "SERVICE_ID from interface org.osgi.framework.Constants")
            .put("service.pid", "SERVICE_PID from interface org.osgi.framework.Constants")
            .put("service.ranking", "SERVICE_RANKING from interface org.osgi.framework.Constants")
            .put("service.vendor", "SERVICE_VENDOR from interface org.osgi.framework.Constants")
            .put("service.description", "SERVICE_DESCRIPTION from interface org.osgi.framework.Constants")

            // class org.apache.sling.engine.EngineConstants
            .put("javax.servlet.Filter", "FILTER_NAME from class org.apache.sling.engine.EngineConstants")
            .put("sling.filter.scope", "SLING_FILTER_SCOPE from class org.apache.sling.engine.EngineConstants")
            .put("COMPONENT", "FILTER_SCOPE_COMPONENT from class org.apache.sling.engine.EngineConstants")
            .put("ERROR", "FILTER_SCOPE_ERROR from class org.apache.sling.engine.EngineConstants")
            .put("INCLUDE", "FILTER_SCOPE_INCLUDE from class org.apache.sling.engine.EngineConstants")
            .put("FORWARD", "FILTER_SCOPE_FORWARD from class org.apache.sling.engine.EngineConstants")
            .put("REQUEST", "FILTER_SCOPE_REQUEST from class org.apache.sling.engine.EngineConstants")

            // class com.adobe.granite.workflow.event.WorkflowEvent
            .put("com/adobe/granite/workflow/event", "EVENT_TOPIC from class com.adobe.granite.workflow.event.WorkflowEvent")

            .build();
    }

    private ConstantsChecker() {
        // empty
    }

    public static boolean isAnnotationConstant(String value) {
        return ANNOTATION_CONSTANTS.containsKey(value);
    }

    public static String getAnnotationMessageForConstant(String value) {
        return ANNOTATION_CONSTANTS.get(value);
    }

    public static String getMessageForConstant(String value) {
        return CONSTANTS.get(value);
    }

    public static boolean isConstant(String value) {
        return CONSTANTS.containsKey(value);
    }

}
