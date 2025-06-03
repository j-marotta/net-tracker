const api       = "/api/assets";
const rows      = document.getElementById("assetRows");
const netDOM    = document.getElementById("networth");
const form      = document.getElementById("assetForm");
const errorBox  = document.getElementById("error");
const linkBtn   = document.getElementById("link-button");

const $ = (sel, ctx = document) => ctx.querySelector(sel);
const money = n => Number(n).toLocaleString("en-US",{style:"currency",currency:"USD"});
const rowHTML = a => {
    const gain    = a.units * a.unitValue - a.units * (a.purchasePrice || 0);
    const gainCls = gain > 0 ? "text-emerald-400" : gain < 0 ? "text-red-400" : "";
    return `
    <tr data-id="${a.id}">
      <td class="py-1 px-3">${a.name}</td>
      <td class="py-1 px-3">${a.category}</td>
      <td class="py-1 px-3 text-right">${a.units}</td>
      <td class="py-1 px-3 text-right">${money(a.purchasePrice || a.unitValue)}</td>
      <td class="py-1 px-3 text-right">${money(a.unitValue)}</td>
      <td class="py-1 px-3 text-right ${gainCls}">${money(gain)}</td>
      <td class="py-1 px-3">${a.updated?.slice(0,19).replace("T"," ") || ""}</td>
      <td class="py-1 px-3 text-center">
        <button class="delete bg-red-800/90 hover:bg-red-700 text-white border border-white/40 rounded-lg px-3 py-1 transition" data-id="${a.id}">✕</button>
      </td>
    </tr>`;
};

async function loadAssets() {
    const list = await fetch(api).then(r=>r.json());
    rows.innerHTML = list.map(rowHTML).join("");
    attachDeleteHandlers();
    updateNetWorth();
}

async function updateNetWorth() {
    const n = await fetch(`${api}/networth`).then(r=>r.json());
    netDOM.textContent = money(n);
}

form.addEventListener("submit", async e => {
    e.preventDefault();
    errorBox.textContent = "";
    const body = {
        name          : $("#name").value.trim().toUpperCase(),
        category      : $("#category").value,
        units         : Number($("#units").value),
        purchasePrice : Number($("#purchasePrice").value)
    };
    try {
        const res = await fetch(api,{
            method :"POST",
            headers:{ "Content-Type":"application/json" },
            body   : JSON.stringify(body)
        });
        if(!res.ok) throw new Error(await res.text());
        form.reset();
        loadAssets();
    } catch(err) { showToast(err.message || "Something went wrong"); }
});

function showToast(message, ms = 4000){
    const toast = $("#toast");
    const msg   = $("#toast-msg");
    const bar   = $("#toast-progress");
    msg.textContent = message;
    toast.classList.remove("hidden");
    bar.style.transition="none";
    bar.style.width="100%";
    void bar.offsetWidth;
    bar.style.transition=`width ${ms}ms linear`;
    bar.style.width="0%";
    const hide=()=>{
        toast.classList.add("hidden");
        bar.style.transition="none";
        toast.removeEventListener("click",hide);
    };
    setTimeout(hide,ms);
    toast.addEventListener("click",hide);
}

function attachDeleteHandlers(){
    rows.querySelectorAll(".delete").forEach(btn =>
        btn.onclick = async () => {
            await fetch(`${api}/${btn.dataset.id}`,{method:"DELETE"});
            loadAssets();
        });
}

async function initializePlaid() {
    const resp = await fetch('/api/plaid/link_token');
    const { link_token } = await resp.json();
    const handler = Plaid.create({
        token: link_token,
        onSuccess: async function(public_token, metadata) {
            await fetch('/api/plaid/exchange', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ public_token }),
            });
            showToast("Bank account linked");
        },
        onExit: function(err, metadata) {
            if (err != null) {
                showToast("Plaid Link error");
            }
        }
    });
    linkBtn.onclick = () => handler.open();
}

document.addEventListener("DOMContentLoaded",()=>{
    initializePlaid();
    loadAssets();
    setInterval(loadAssets,5*60*1000);
});