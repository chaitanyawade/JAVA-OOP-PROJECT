package com.antigravity.service;

import com.antigravity.model.AssetNode;
import com.antigravity.model.Version;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class VersionManager {

    /**
     * Finalizes a file uploaded from chat, cloning metadata, incrementing version,
     * and attaching it to the specific AssetNode log.
     * 
     * @param assetNode The specific object tracing the lifecycle of this asset
     * @param finalizedByUserId The user (Manager/Approver) triggering the finalize
     * @param fileDataOrPath For zero budget MVP, uses a Base64 string or local file path
     * @param metadata Description and properties of this version
     * @return Transformed AssetNode to be saved as the newly updated source of truth
     */
    public AssetNode finalizeFileToVault(AssetNode assetNode, String finalizedByUserId, String fileDataOrPath, String metadata) {
        // Calculate the next vNumber
        int nextVersionNumber = assetNode.getVersions().size() + 1;
        
        // Clone metadata and build the actual version record
        Version newVersion = new Version();
        newVersion.setVersionId("v" + nextVersionNumber);
        newVersion.setFinalizedBy(finalizedByUserId);
        newVersion.setFinalizedAt(Instant.now());
        newVersion.setStoragePath(fileDataOrPath);
        newVersion.setMetadata("Cloned Metadata: " + assetNode.getContent() + " | Finalized Note: " + metadata);
        
        // Attach newly finalized item back exactly onto the specific Asset chain
        assetNode.getVersions().add(newVersion);
        assetNode.setCurrentVersionId(newVersion.getVersionId());
        
        // At this layer, a Repository hook (e.g. assetNodeRepository.save(assetNode))
        // should be utilized to persist this specific asset into MongoDB.
        
        return assetNode;
    }
}
