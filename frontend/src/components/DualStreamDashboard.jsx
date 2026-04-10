import React, { useState, useEffect } from 'react';
// import { axios } from 'axios'; // Normally this would be utilized for requests

// DualStreamDashboard Component merging live WhatsApp-style messaging and Vault version control display
const DualStreamDashboard = ({ teamId }) => {
    const [liveStreamDetails, setLiveStreamDetails] = useState([]);
    const [sourceOfTruthAssets, setSourceOfTruthAssets] = useState([]);
    const [newMessage, setNewMessage] = useState('');

    // Implementing a local-storage simulation to simulate DB records dynamically via polling MVP without websockets
    const STORAGE_KEY_CHAT = `antigravity_chat_${teamId}`;
    const STORAGE_KEY_VAULT = `antigravity_vault_${teamId}`;

    useEffect(() => {
        // Initialize default arrays locally if empty
        if (!localStorage.getItem(STORAGE_KEY_CHAT)) localStorage.setItem(STORAGE_KEY_CHAT, JSON.stringify([]));
        if (!localStorage.getItem(STORAGE_KEY_VAULT)) localStorage.setItem(STORAGE_KEY_VAULT, JSON.stringify([]));

        const fetchTeamData = async () => {
            try {
                // In a fully integrated environment, we fetch here:
                // const chatRes = await axios.get(`/api/teams/${teamId}/chat`);
                // const vaultRes = await axios.get(`/api/teams/${teamId}/vault`);
                
                // For MVP Simulation, extracting from zero-budget local storage simulated DBs:
                const localChat = JSON.parse(localStorage.getItem(STORAGE_KEY_CHAT) || '[]');
                const localVault = JSON.parse(localStorage.getItem(STORAGE_KEY_VAULT) || '[]');
                
                setLiveStreamDetails(localChat);
                setSourceOfTruthAssets(localVault);
            } catch (error) {
                console.error("Failed to fetch team data", error);
            }
        };

        const intervalId = setInterval(fetchTeamData, 2000); // Poll updating mechanisms exactly every 2 seconds matching the scope
        fetchTeamData(); // Initial load wrapper immediately fetches the content
        
        return () => clearInterval(intervalId); // Cleanup Polling mechanism upon breakdown
    }, [teamId]);

    const handleSendMessage = () => {
        if (!newMessage) return;
        
        const freshMessage = { id: Date.now(), content: newMessage, type: 'CHAT', author: 'LocalUser' };
        
        // Mock post request processing here using localStorage. Normally: axios.post('/chat', freshMessage)
        const updatedChat = [...liveStreamDetails, freshMessage];
        localStorage.setItem(STORAGE_KEY_CHAT, JSON.stringify(updatedChat));
        setLiveStreamDetails(updatedChat);
        
        // Optional Mock File simulating logic allowing file uploads to trigger "finalize request"
        if (newMessage.includes('.pdf') || newMessage.includes('.png')) {
            const fileUpload = { id: Date.now() + 1, content: newMessage, type: 'FILE', author: 'LocalUser' };
            const delayedUpload = [...updatedChat, fileUpload];
            localStorage.setItem(STORAGE_KEY_CHAT, JSON.stringify(delayedUpload));
            setLiveStreamDetails(delayedUpload);
        }

        setNewMessage('');
    };

    const handleFinalizeToVault = (node) => {
        // Simulates version incrementer taking action. In prod, Axios POST requests to VersionManager java Spring route goes here
        const newAssetNode = { 
            ...node, 
            currentVersionId: 'v1', 
            finalizedBy: 'CurrentManager',
            id: Date.now() 
        };
        
        const finalVaultData = [...sourceOfTruthAssets, newAssetNode];
        localStorage.setItem(STORAGE_KEY_VAULT, JSON.stringify(finalVaultData));
        setSourceOfTruthAssets(finalVaultData);
    };

    return (
        <div className="flex h-screen bg-[#121212] text-white font-sans">
            {/* Left Box Simulation: Live Stream Event Polling (Equivalent to Fast Chat Systems) */}
            <div className="w-1/2 flex flex-col border-r border-gray-800">
                <header className="px-6 py-4 bg-[#1f1f1f] text-lg font-bold border-b border-gray-800 flex items-center gap-3">
                    <span className="w-3 h-3 rounded-full bg-blue-500 shrink-0"></span>
                    Live Stream
                </header>
                <div className="flex-1 overflow-y-auto p-6 space-y-4 bg-[url('https://www.transparenttextures.com/patterns/cubes.png')] bg-opacity-20">
                    {liveStreamDetails.map(chat => (
                        <div key={chat.id} className="p-4 bg-[#232323] rounded-2xl max-w-xl shadow-md border border-gray-800">
                            <p className="text-[15px] leading-relaxed">{chat.content}</p>
                            
                            {/* If a node specifically carries "FILE" it can be mapped to vault extraction directly via the UI block button */}
                            {chat.type === 'FILE' && (
                                <button 
                                    onClick={() => handleFinalizeToVault(chat)}
                                    className="mt-3 px-4 py-1.5 bg-indigo-600 hover:bg-indigo-500 rounded-lg text-sm font-semibold transition-all duration-200"
                                >
                                    Finalize into Vault &rarr;
                                </button>
                            )}
                        </div>
                    ))}
                    
                    {/* Simulated Prompt Helper Notice */}
                    {liveStreamDetails.length === 0 && (
                        <div className="text-gray-500 text-sm mt-8 opacity-70 italic w-full text-center">
                            Write a message. Append ".pdf" to any text dynamically tracking file behavior inside stream...
                        </div>
                    )}
                </div>
                
                {/* Input form area */}
                <div className="p-4 bg-[#1a1a1a] border-t border-gray-800 flex shadow-[0_-10px_20px_rgba(0,0,0,0.2)]">
                    <input 
                        type="text" 
                        value={newMessage}
                        onChange={(e) => setNewMessage(e.target.value)}
                        onKeyDown={(e) => e.key === 'Enter' && handleSendMessage()}
                        className="flex-1 px-5 py-3.5 bg-[#2c2c2c] rounded-l-xl focus:outline-none placeholder-gray-500 focus:ring-1 focus:ring-indigo-500 text-[15px]"
                        placeholder="Push your updates to the fast track network..."
                    />
                    <button 
                        onClick={handleSendMessage}
                        className="px-8 py-3.5 bg-blue-600 hover:bg-blue-500 rounded-r-xl font-bold transition-all text-white shadow-lg shadow-blue-900/50"
                    >
                        Send
                    </button>
                </div>
            </div>

            {/* Right Box Simulation: The Vault. Render Source of Truth Assets exclusively */}
            <div className="w-1/2 flex flex-col bg-[#0d0d0d] relative shadow-[-10px_0_30px_rgba(0,0,0,0.5)]">
                <header className="px-6 py-4 bg-[#1f1f1f] border-b border-gray-800 flex justify-between items-center z-10 box-shadow">
                    <span className="font-bold text-green-400 text-lg flex items-center gap-2">
                        <svg className="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" /></svg>
                        The Vault
                    </span>
                    <span className="text-xs px-3 py-1 bg-green-500/10 text-green-400 rounded border border-green-500/30 font-mono tracking-wider shadow-sm">
                        SOURCE_OF_TRUTH
                    </span>
                </header>
                <div className="flex-1 overflow-y-auto p-8 grid grid-cols-1 md:grid-cols-2 gap-5 content-start">
                    {sourceOfTruthAssets.length === 0 ? (
                        <div className="col-span-full h-full flex flex-col items-center justify-center text-gray-500 pt-32 p-8 border-2 border-dashed border-gray-800 rounded-2xl">
                            <svg className="w-16 h-16 mb-4 text-gray-700" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1} d="M9 13h6m-3-3v6m5 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" /></svg>
                            <span className="text-lg">Vault is currently empty.</span>
                            <span className="text-sm">Finalize a file over the chat thread to automatically populate git-styled nodes here.</span>
                        </div>
                    ) : (
                        sourceOfTruthAssets.map(asset => (
                            <div key={asset.id} className="p-5 bg-gradient-to-br from-[#1a1a1a] to-[#222] rounded-2xl border border-gray-800 hover:border-green-500/50 hover:shadow-[0_0_15px_rgba(34,197,94,0.15)] transition-all group flex flex-col justify-between min-h-[140px]">
                                <div>
                                    <div className="flex justify-between items-start mb-3">
                                        <h3 className="font-semibold text-[15px] truncate max-w-[70%] text-gray-100">{asset.content}</h3>
                                        <span className="px-2 py-0.5 bg-[#111] border border-gray-700 text-[11px] rounded text-emerald-400 font-mono tracking-widest shadow-inner">
                                            {asset.currentVersionId}
                                        </span>
                                    </div>
                                    <p className="text-xs text-gray-500">Node Asset ID: {asset.id}</p>
                                </div>
                                <div className="text-[11px] pt-4 mt-auto border-t border-gray-800 text-gray-400 flex justify-between items-end w-full">
                                    <span className="flex items-center gap-1">
                                        <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" /></svg>
                                        Manager: {asset.finalizedBy}
                                    </span>
                                    <button className="text-gray-500 hover:text-white underline decoration-gray-600 hover:decoration-white transition-all">
                                        Inspect History
                                    </button>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default DualStreamDashboard;
